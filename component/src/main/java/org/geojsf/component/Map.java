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

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.event.MapAjaxEvent;
import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.xml.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({
	  @ResourceDependency(library = "geojsf",
	      name = "GeoJSF.js", target = "head"),
	      @ResourceDependency(library = "javax.faces",
	      name = "jsf.js", target = "head")}
	)
@FacesComponent(value="org.geojsf.component.Map")
public class Map  extends UINamingContainer implements ClientBehaviorHolder{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private ArrayList<DefaultGeoJsfService> serviceList;
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private String width = null;
	private String height = null;
	private String centerX = null;
	private String centerY = null;
	private String zoomLevel = null;
	
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>> 
		String layerString(SERVICE service)
	{
		List<LAYER> layers = service.getLayer();
		StringBuffer sb = new StringBuffer();
		for (LAYER layer : layers)
		{
			sb.append(layer.getCode() +",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String fallback()
	{
		logger.info("looking for children of this Map.");
		logger.info("no of children: " +this.getChildCount());
		logger.info("no of children: " +this.getChildren());
		 for (UIComponent child : this.getChildren())
		 {
			 logger.error("Found child of type: " +child.getClass().getSimpleName());
		 }
		 serviceList = new ArrayList<DefaultGeoJsfService>();
		 logger.debug("Checking value existence ...");
		 if (getAttributes().get("value")==null)
		 {
			 logger.debug("No value given - falling back to simple version");
			 String url = (String) getAttributes().get("simpleWmsUrl");
			 DefaultGeoJsfService service = new DefaultGeoJsfService();
			 service.setUrl(url);
			 service.setCode("BaseLayer");
			 Object layerList = getAttributes().get("simpleLayers");
			 logger.debug("Detecting layer definition type..." +layerList.getClass().getSimpleName());
			 if (layerList.getClass().getSimpleName().equals("String"))
			 {
				 logger.debug("Detected layer list given as Strings");
				 String layerString = (String)layerList;
				 for (String string : layerString.split(","))
				 {
					 DefaultGeoJsfLayer layer = new DefaultGeoJsfLayer();
					 layer.setCode(string);
					 service.getLayer().add(layer);
				 }
			 }
			 ArrayList<DefaultGeoJsfService> singleValue = new ArrayList<DefaultGeoJsfService>();
			 singleValue.add(service);
			 getAttributes().put("value", singleValue);
			 serviceList.add(service);
		 }
		 else
		 {
			 GeoJsfMap map = (GeoJsfMap) getAttributes().get("value");
			 serviceList = (ArrayList<DefaultGeoJsfService>) map.getLayerServices();
		 }
		return new String();
	}
	
	@Deprecated
	public void listenRedirect(javax.faces.event.ValueChangeEvent evt)
    {
		logger.debug("Value of coordinates changed by click event. Changed from: " +evt.getOldValue() +" to " +evt.getNewValue());
		coords = new Coordinates(); 
		coords.setValue(evt.getNewValue().toString());
		try {
		//Invoke the given listener that takes the coordinates
		FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression coordinatesListener = (MethodExpression) getAttributes().get("listener");
        Object[] arguments = new Object[1];
        arguments[0] = coords;
        coordinatesListener.invoke(context.getELContext(), arguments);
		}
		catch(Exception ex){
			logger.error("no coordinates listener set - please set the listener attribute to point to a method that accepts org.geojsf.xml.gml.Coordinates");
		}
    }
	
	public ArrayList<DefaultGeoJsfService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList<DefaultGeoJsfService> serviceList) {
		this.serviceList = serviceList;
	}

	 public void decode(FacesContext context) {
	    	logger.info("Entering decode");
		    java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		    String behaviorEvent = params.get("javax.faces.behavior.event");
	        logger.info("Got this event: " +behaviorEvent);
	       
		       
			java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
	        if (behaviors.isEmpty()) {
	           logger.error("no behaviors.exiting.");
	           return;
	        }
	   
	           
        if (behaviorEvent != null) {
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
        }}}}}
	

	@Override
	public Collection<String> getEventNames() {
		ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		return events;
	}

	@Override
	public String getDefaultEventName() {
		return "mapClick";
	}

	public Coordinates getCoords() {
		return coords;
	}

	public void setCoords(Coordinates coords) {
		this.coords = coords;
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		fallback();
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("div", this);
		writer.writeAttribute("id", this.getClientId(), null);
		writer.writeAttribute("style", "width: " +getWidth() +"px; height: " +getHeight() + "px;", null);
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
 		for (DefaultGeoJsfService service : serviceList)
 		{
 			
 			writer.writeText("var url    = '" +service.getUrl() +"';",null);
 		    writer.writeText("var name   = '" +service.getCode() +"';",null);
 			writer.writeText("var params = {};", null);
 			writer.writeText("params.layers      = '"+layerString(service) +"';",null);
 			writer.writeText("params.transparent = 'true';",null);
 			writer.writeText("params.format      = 'image/png';",null);
 			writer.writeText("var options = {};",null);
 			writer.writeText("options.isBaseLayer= '" +baseLayer.toString() +"';", null);
 			writer.writeText("GeoJSF.addLayer(name, url, params, options);",null);
 			baseLayer = false;
 		}
 		writer.writeText("GeoJSF.center(" +getCenterX() +"," +getCenterY() +"," +getZoomLevel() +");", null);
 		writer.endElement("script");   
	}

	public String getWidth() {
		if (this.width!=null)
		{
			return this.width;
		}
		return "500";
	}

	public void setWidth(String width) {		
		this.width = width;
	}

	public String getHeight() {
		if (this.height!=null)
		{
			return this.height;
		}
		return "400";
	}

	public void setHeight(String height) {
		this.height = height;
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
}
