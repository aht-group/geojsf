package org.geojsf.component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.event.MapAjaxEvent;
import org.geojsf.exception.UnconsistentConfgurationException;
import org.geojsf.factory.txt.TxtOpenlayersLayerFactory;
import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.util.GeoJsfJsLoader;
import org.geojsf.xml.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.Map")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Map extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private List<GeoJsfService> serviceList;
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private Integer width = null;
	private Integer height = 400;
	private Boolean noLayersGiven = false;
	private Boolean hasTimeDefinition = false;
	private Date timeInfo = null;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"OpenLayers.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJSF.js");
		}
		super.processEvent(event);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String fallback() throws Exception
	{
		 serviceList = new ArrayList<GeoJsfService>();
		 logger.debug("Checking value existence ...");
		 if (getAttributes().get("value")==null)
		 {
			 if (!containsLayer())
			 {
				 throw new Exception("No value or layers given!");
			 }
			 else
			 {
				 setBaseLayer();
				 logger.debug("No value given - falling back to simple version");
				 for (UIComponent child : this.getChildren())
				 {
					 logger.info("Found child of type: " +child.getClass().getSimpleName());
					 if (child.getClass().getSimpleName().equals("Layer"))
					 {
						 Layer layer = (Layer) child;
						 addLayerToList(layer);
					 }
				 } 
			 }
		 }
		 else
		 {
			 GeoJsfMap map = (GeoJsfMap) getAttributes().get("value");
			 serviceList = map.getLayerServices();
			 if (serviceList.size()==0)
			 {
				 noLayersGiven = true;
			 }
			 if (containsLayer())
			 {
				 throw new UnconsistentConfgurationException("layer tag found while value is given. Don't mix configurations!");
			 }
		 }
		 searchTimeDefinition();
		return new String();
	}
	
	public Boolean containsLayer()
	{
		Boolean layer = false;
		for (UIComponent child : this.getChildren())
		 {
			 if (child instanceof Layer)
			 {
				 layer = true;
			 }
		 }
		return layer;
	}
	
	public void addLayerToList(Layer layer)
	{
		 String url = (String) layer.getUrl();
		 DefaultGeoJsfService service = new DefaultGeoJsfService();
		 service.setUrl(url);
		 service.setCode(layer.getName());
		 Boolean temporal = layer.getHasTemporalLayer();
		 Object layerList = layer.getLayers();
		 logger.debug("Detecting layer definition type..." +layerList.getClass().getSimpleName());
		 if (layerList.getClass().getSimpleName().equals("String"))
		 {
			 logger.debug("Detected layer list given as Strings");
			 String layerString = (String)layerList;
			 for (String string : layerString.split(","))
			 {
				 DefaultGeoJsfLayer layerToAdd = new DefaultGeoJsfLayer();
				 layerToAdd.setTemporalLayer(temporal);
				 layerToAdd.setCode(string);
				 service.getLayer().add(layerToAdd);
			 }
		 }
		 ArrayList<DefaultGeoJsfService> singleValue = new ArrayList<DefaultGeoJsfService>();
		 singleValue.add(service);
		 getAttributes().put("value", singleValue);
		 serviceList.add(service);
	}
	
	@Deprecated
	public void listenRedirect(javax.faces.event.ValueChangeEvent evt)
    {
		logger.debug("Value of coordinates changed by click event. Changed from: " +evt.getOldValue() +" to " +evt.getNewValue());
		coords = new Coordinates(); 
		coords.setValue(evt.getNewValue().toString());
		try
		{  //Invoke the given listener that takes the coordinates
			FacesContext context = FacesContext.getCurrentInstance();
			MethodExpression coordinatesListener = (MethodExpression) getAttributes().get("listener");
			Object[] arguments = new Object[1];
			arguments[0] = coords;
			coordinatesListener.invoke(context.getELContext(), arguments);
		}
		catch(Exception e)
		{
			logger.error("no coordinates listener set - please set the listener attribute to point to a method that accepts org.geojsf.xml.gml.Coordinates");
		}
    }

	public void decode(FacesContext context)
	{
		logger.info("Entering decode");
		java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");
	    logger.info("Got this event: " +behaviorEvent);
	              
		java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.isEmpty())
		{
			logger.error("no behaviors.exiting.");
			return;
		}
	           
        if (behaviorEvent != null)
        {
            List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);
            if (behaviors.size() > 0)
            {
            	String behaviorSource = params.get("javax.faces.source");
            	String clientId = getClientId(context);
            	if (behaviorSource != null && behaviorSource.equals(clientId))
            	{
            		for (ClientBehavior behavior: behaviorsForEvent)
            		{
            			logger.info("Found " +behavior.getClass().toString());
            			MapAjaxEvent ajaxEvent = new MapAjaxEvent(this, behavior);
            			String lat = params.get("org.geojsf.coordinates.lat");
            			String lon = params.get("org.geojsf.coordinates.lon");
            			ajaxEvent.setLatLon(lat,lon);
            			behavior.broadcast(ajaxEvent);
            		}
            	}
            }
        }
	}
	

	@Override
	public Collection<String> getEventNames()
	{
		ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		return events;
	}

	@Override
	public String getDefaultEventName()
	{
		return "mapClick";
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		try {
			fallback();
		} catch (UnconsistentConfgurationException e) {
			logger.warn("Problem occured when processing layers: " +e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.setRendered(false);
		}
		if (this.isRendered())
		{
			ResponseWriter writer = ctx.getResponseWriter();
			writer.writeText(System.getProperty("line.separator"), null);
			writer.writeText(System.getProperty("line.separator"), null);
			
			writer.startElement("div", this);
			writer.writeAttribute("id", this.getClientId(), null);
			writer.writeAttribute("style", buildStyle(), null);
			writer.writeAttribute("id", this.getClientId(), null);
			writer.endElement("div");
			writer.writeText(System.getProperty("line.separator"), null);
			
			if (noLayersGiven)
			{
				writer.startElement("center", this);
				writer.writeText("no layers given.", null);
				writer.endElement("center");
			}
			else
			{
				writer.writeText(System.getProperty("line.separator"), null);
				writer.startElement("script", this);
				writer.writeText(System.getProperty("line.separator"), null);
				writer.writeText("// GeoJSF: Initializing OpenLayers map"+System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.bootstrap();" +System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.addClickHandler('" +this.getClientId() +"','" +this.getClientId() +":resetLayers');" +System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.initMap('" +this.getClientId() +"','');" +System.getProperty("line.separator"), null);
				if (this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME")!=null)
				{
					writer.writeText("OpenLayers.ImgPath='" +this.getFacesContext().getExternalContext().getRequestContextPath() +"/" +this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME") +"/';" +System.getProperty("line.separator"), null);
				}
			    
				writer.writeText("GeoJSF.resetLayers();", null);
				writer.writeText(System.getProperty("line.separator"), null);
				writer.writeText(System.getProperty("line.separator"), null);
				writer.writeText("// GeoJSF: Adding layers"+System.getProperty("line.separator"), null);
				writer.writeText("// GeoJSF: The last given layer will be taken as base layer:"+System.getProperty("line.separator"), null);
				
				//First, add the last layer as base layer - adding overlays first results in error
				GeoJsfService baseLayer = serviceList.get(serviceList.size()-1);
				encodeLayer(baseLayer, true, writer);
				logger.info("Service list has " +serviceList.size() +" elements.");
				//Now add the overlay layers
//				for (int i=0;i<serviceList.size()-1;i++)
		 		for(int i=serviceList.size()-2;i==0;i--) //GEO-64
				{	
					GeoJsfService service = serviceList.get(i);
		 			encodeLayer(service, false, writer);
		 		}
			}
		}
		  
	}
	
	public Boolean hasTemporalLayer(GeoJsfService service)
	{
		Boolean temporal = false;
		for (Object o : service.getLayer())
		{
			GeoJsfLayer layer = (GeoJsfLayer) o;
			if (layer.isTemporalLayer()) {temporal = true;}
		}
		return temporal;
	}
	
	public void searchTimeDefinition()
	{
		for (UIComponent comp : this.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("Time"))
			{
				Time t = (Time) comp;
				try {
					timeInfo = t.getDate();
					logger.debug("Getting date from Time component: "+timeInfo.toGMTString());
				} catch (ParseException e) {
					logger.error("Could not get date from Time component:" +e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void encodeEnd(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		writer.endElement("script"); 
	}
	
	public void encodeLayer(GeoJsfService service, Boolean baseLayer, ResponseWriter writer) throws IOException
	{
		String sLayers = TxtOpenlayersLayerFactory.buildLayerString(service);
		logger.info("Adding "+service.getCode()+": "+sLayers);
		writer.writeText("var url    = '" +service.getUrl() +"';" +System.getProperty("line.separator"),null);
	    writer.writeText("var name   = '" +service.getCode() +"';" +System.getProperty("line.separator"),null);
		writer.writeText("var params = {};" +System.getProperty("line.separator"), null);
		writer.writeText("params.layers      = '"+sLayers+"';" +System.getProperty("line.separator"),null);
		
		if (hasTemporalLayer(service) && null!=timeInfo)
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			String timeString = df.format(timeInfo);
			writer.writeText("params.time      = '"+timeString +"';" +System.getProperty("line.separator"),null);
		}
		
		writer.writeText("params.transparent = true;" +System.getProperty("line.separator"),null);
		writer.writeText("params.format      = 'image/png';" +System.getProperty("line.separator"),null);
		writer.writeText("var options = {};" +System.getProperty("line.separator"),null);
		writer.writeText("options.isBaseLayer = " +baseLayer +";" +System.getProperty("line.separator"), null);
		writer.writeText("GeoJSF.addLayer(name, url, params, options);" +System.getProperty("line.separator"),null);
		writer.writeText(System.getProperty("line.separator"), null);
	}
	
	public void setBaseLayer()
	{
		Integer lastLayerIndex = 0;
		ArrayList<UIComponent> children = (ArrayList<UIComponent>) this.getChildren();
		for (int i=0; i<children.size(); i++)
	//	for (UIComponent child : this.getChildren())
		{
			UIComponent child = children.get(i);
			 if (child.getClass().getSimpleName().equals("Layer"))
			 {
				 lastLayerIndex = i;
				 Layer layer = (Layer) child;
				 if (null==layer.getName())
				 {
					 layer.setName("Layer" +i);
				 }
			 }
		 }
		Layer baseLayer = (Layer) this.getChildren().get(lastLayerIndex);
		baseLayer.setIsBaseLayer(true);
	}

	
	private String buildStyle()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("width: ");
		if(width!=null){sb.append(width +"px;");}
		else{sb.append("100%;");}
		sb.append("height: " +height + "px;");
		return sb.toString();
	}
	
	public Integer getWidth(){return width;}
	public void setWidth(Integer width) {this.width = width;}
	
	public Integer getHeight() {return height;}
	public void setHeight(Integer height) {this.height = height;}
	
	public Coordinates getCoords() {return coords;}
	public void setCoords(Coordinates coords) {this.coords = coords;}
	
	public List<GeoJsfService> getServiceList() {return serviceList;}
	public void setServiceList(ArrayList<GeoJsfService> serviceList) {this.serviceList = serviceList;}
}