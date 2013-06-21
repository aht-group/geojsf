package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import org.geojsf.factory.txt.TxtOpenlayersLayerFactory;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.util.GeoJsfJsLoader;
import org.geojsf.xml.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head")})
@FacesComponent(value="org.geojsf.component.Map")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Map extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private ArrayList<GeoJsfService> serviceList;
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private Integer width = null;
	private Integer height = 400;
	private String centerX = null;
	private String centerY = null;
	private String zoomLevel = null;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJSF.js");
		}
		super.processEvent(event);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String fallback()
	{
		 serviceList = new ArrayList<GeoJsfService>();
		 logger.debug("Checking value existence ...");
		 if (getAttributes().get("value")==null)
		 {
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
		 else
		 {
			 GeoJsfMap map = (GeoJsfMap) getAttributes().get("value");
			 serviceList = (ArrayList<GeoJsfService>) map.getLayerServices();
		 }
		return new String();
	}
	
	public void addLayerToList(Layer layer)
	{
		 String url = (String) layer.getUrl();
		 DefaultGeoJsfService service = new DefaultGeoJsfService();
		 service.setUrl(url);
		 service.setCode("BaseLayer");
		 Object layerList = layer.getLayers();
		 logger.debug("Detecting layer definition type..." +layerList.getClass().getSimpleName());
		 if (layerList.getClass().getSimpleName().equals("String"))
		 {
			 logger.debug("Detected layer list given as Strings");
			 String layerString = (String)layerList;
			 for (String string : layerString.split(","))
			 {
				 DefaultGeoJsfLayer layerToAdd = new DefaultGeoJsfLayer();
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
            if (behaviors.size() > 0) {
                String behaviorSource = params.get("javax.faces.source");
               String clientId = getClientId(context);
               if (behaviorSource != null && behaviorSource.equals(clientId)) {
             	  for (ClientBehavior behavior: behaviorsForEvent) {
             		  logger.info("Found " +behavior.getClass().toString());
             		  MapAjaxEvent ajaxEvent = new MapAjaxEvent(this, behavior);
             		  String coordinates = params.get("org.geojsf.coordinates");
           			  ajaxEvent.setCoordinates(coordinates);
             		  behavior.broadcast(ajaxEvent);
        }}}}
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
		fallback();
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("div", this);
		writer.writeAttribute("id", this.getClientId(), null);
		writer.writeAttribute("style", buildStyle(), null);
		writer.writeAttribute("id", this.getClientId(), null);
		writer.endElement("div");
		
		writer.startElement("script", this);
		writer.writeAttribute("src", "http://openlayers.org/api/OpenLayers.js", null);
		writer.endElement("script");
		
		writer.startElement("script", this);
		writer.writeAttribute("src", "http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js", null);
		writer.endElement("script");
		
		writer.startElement("script", this);
		writer.writeText("GeoJSF.addClickHandler('" +this.getClientId() +"','" +this.getClientId() +":resetLayers');", null);
		writer.writeText("GeoJSF.initMap('" +this.getClientId() +"','');", null);
		if (this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME")!=null)
		{
			writer.writeText("OpenLayers.ImgPath='" +this.getFacesContext().getExternalContext().getRequestContextPath() +"/" +this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME") +"/';", null);
		}
	    
		writer.writeText("GeoJSF.resetLayers();", null);
		Boolean baseLayer = true;
		logger.info("Class ???");
		logger.info("Class :"+serviceList.get(0).getClass().getName());
 		for (GeoJsfService service : serviceList)
 		{
 			logger.info("Adding "+service.getCode());
 			writer.writeText("var url    = '" +service.getUrl() +"';",null);
 		    writer.writeText("var name   = '" +service.getCode() +"';",null);
 			writer.writeText("var params = {};", null);
 			writer.writeText("params.layers      = '"+TxtOpenlayersLayerFactory.buildLayerString(service) +"';",null);
 			writer.writeText("params.transparent = 'true';",null);
 			writer.writeText("params.format      = 'image/png';",null);
 			writer.writeText("var options = {};",null);
 			writer.writeText("options.isBaseLayer= '" +baseLayer.toString() +"';", null);
 			writer.writeText("GeoJSF.addLayer(name, url, params, options);",null);
 			baseLayer = false;
 		}  
	}
	
	@Override
	public void encodeEnd(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		writer.endElement("script"); 
	}

	public String getCenterX() {
		if (this.centerX!=null)
		{
			return this.centerX;
		}
		return "0.0";
	}

	public void setCenterX(String centerX) {
		this.centerX = centerX;
	}

	public String getCenterY() {
		if (this.centerY!=null)
		{
			return this.centerY;
		}
		return "0.0";
	}

	public void setCenterY(String centerY) {
		this.centerY = centerY;
	}

	public String getZoomLevel() {
		if (this.zoomLevel!=null)
		{
			return this.zoomLevel;
		}
		return "5";
	}

	public void setZoomLevel(String zoomLevel) {
		this.zoomLevel = zoomLevel;
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
	
	public ArrayList<GeoJsfService> getServiceList() {return serviceList;}
	public void setServiceList(ArrayList<GeoJsfService> serviceList) {this.serviceList = serviceList;}
}