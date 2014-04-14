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
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.event.MapAjaxEvent;
import org.geojsf.exception.UnconsistentConfgurationException;
import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.txt.TxtIsoTimeFactory;
import org.geojsf.factory.txt.TxtOpenlayersLayerFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.util.GeoJsfJsLoader;
import org.geojsf.xml.geojsf.Scales;
import org.geojsf.xml.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.Map")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Map
	   extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private List<DefaultGeoJsfService> serviceList;
	private List<String> temporalLayerNames;
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private Integer width = null;
	private Integer height = 400;
	private Boolean noLayersGiven = false;
	private Boolean hasTimeDefinition = false;
	private String timeInfo = null;
	private Scales scales = null;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"OpenLayers.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJSF.js");
			
			try {
				MapUtil.initLayerConfiguration(this);
			} catch (UnconsistentConfgurationException e) {
				logger.warn("Problem occured when processing layers: " +e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				this.setRendered(false);
			}
		}
		super.processEvent(event);
	}
	
	
	// --------------------------
	// JSF Encode Phase methods
	// --------------------------

	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		
		if (this.isRendered())
		{
			ResponseWriter writer = ctx.getResponseWriter();
			JsfRenderUtil renderer = new JsfRenderUtil(writer, this);
			renderer.renderLinebreaks(2);
			renderer.renderDiv(this.getClientId(), MapUtil.buildStyle(height, width));
			
			writer.writeText(System.getProperty("line.separator"), null);
			
			if (serviceList.isEmpty())
			{
				writer.startElement("center", this);
				writer.writeText("no layers given.", null);
				writer.endElement("center");
			}
			else
			{
				renderer.renderLinebreaks(1);
				writer.startElement("script", this);
				writer.writeText("jsf.ajax.addOnEvent(GeoJSF.ajaxResponse);", null);
				renderer.renderLinebreaks(1);
				writer.writeText("// GeoJSF: Initializing OpenLayers map"+System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.bootstrap();" +System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.addClickHandler('" +this.getClientId() +"','" +this.getClientId() +":resetLayers','" +JsfRenderUtil.encodeAjax(this) +"');" +System.getProperty("line.separator"), null);
				writer.writeText("GeoJSF.initMap('" +this.getClientId() +"','');" +System.getProperty("line.separator"), null);
				if (this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME")!=null)
				{
					writer.writeText("OpenLayers.ImgPath='" +this.getFacesContext().getExternalContext().getRequestContextPath() +"/" +this.getFacesContext().getExternalContext().getInitParameter("geojsf.THEME") +"/';" +System.getProperty("line.separator"), null);
				}
			    
				writer.writeText("GeoJSF.resetLayers();", null);
				renderer.renderLinebreaks(2);
				writer.writeText("// GeoJSF: Adding layers"+System.getProperty("line.separator"), null);
				writer.writeText("// GeoJSF: The last given layer will be taken as base layer:"+System.getProperty("line.separator"), null);
				
				//First, add the last layer as base layer - adding overlays first results in error
				GeoJsfService baseLayer = serviceList.get(serviceList.size()-1);
				encodeLayer(baseLayer, true, writer);
				logger.info("Service list has " +serviceList.size() +" elements.");
				//Now add the overlay layers
				//for (int i=0;i<serviceList.size()-1;i++)
		 		for(int i=serviceList.size()-2;i==0;i--) //GEO-64
				{	
					GeoJsfService service = serviceList.get(i);
		 			encodeLayer(service, false, writer);  
	} } } }
	
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
	    writer.writeText("var name   = '" +service.getId() +"';" +System.getProperty("line.separator"),null);
		writer.writeText("var params = {};" +System.getProperty("line.separator"), null);
		writer.writeText("params.layers      = '"+sLayers+"';" +System.getProperty("line.separator"),null);
		ArrayList<String> localTemporalLayer = MapUtil.hasTemporalLayer(service);
		if (localTemporalLayer.size()>0 && null!=timeInfo)
		{
			temporalLayerNames.addAll(localTemporalLayer);
			writer.writeText("params.time      = '"+timeInfo +"';" +System.getProperty("line.separator"),null);
		}
		
		writer.writeText("params.transparent = true;" +System.getProperty("line.separator"),null);
		writer.writeText("params.format      = 'image/png';" +System.getProperty("line.separator"),null);
		writer.writeText("var options = {};" +System.getProperty("line.separator"),null);
		writer.writeText("options.isBaseLayer = " +baseLayer +";" +System.getProperty("line.separator"), null);
		
	//	writer.writeText("options.scales = [10, 10000, 100000];" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.maxScale = 10;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.minScale = 100000;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.units = 'm';" +System.getProperty("line.separator"), null);
		writer.writeText("GeoJSF.addLayer(name, url, params, options);" +System.getProperty("line.separator"),null);
		writer.writeText(System.getProperty("line.separator"), null);
	}
	
	
	// --------------------------
	// JSF Decode Phase method
	// --------------------------
	
	public void decode(FacesContext context)
	{
		java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");
	    logger.info("Handling event of type: " +behaviorEvent +" in decode phase.");
	              
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
            			String scl = params.get("org.geojsf.coordinates.scale");
            			ajaxEvent.setLatLon(lat,lon);
            			ajaxEvent.setScale(scl);
            			behavior.broadcast(ajaxEvent);
	} } } } }
	
	
	// -------------------------------------------
	// JSF Methods for State Saving and Event Name
	// -------------------------------------------
	
	@Override
	public void restoreState(FacesContext context, Object state) {
	    
	}
	
	@Override
	public Object saveState(FacesContext context) {
	    Object[] rtrn = new Object[2];
	    
	    return rtrn;
	}
	
	@Override
	public Collection<String> getEventNames()
	{
		ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		return events;
	}

	@Override
	public String getDefaultEventName() {return "mapClick";}
	
	
	// --------------------------
	// Standard Getter and Setter
	// --------------------------
	
	public Integer getWidth(){return width;}
	public void setWidth(Integer width) {this.width = width;}
	
	public Integer getHeight() {return height;}
	public void setHeight(Integer height) {this.height = height;}
	
	public Coordinates getCoords() {return coords;}
	public void setCoords(Coordinates coords) {this.coords = coords;}

	public List<DefaultGeoJsfService> getServiceList() {return serviceList;}
	public void setServiceList(List<DefaultGeoJsfService> serviceList) {this.serviceList = serviceList;}

	public List<String> getTemporalLayerNames() {return temporalLayerNames;}
	public void setTemporalLayerNames(List<String> temporalLayerNames) {this.temporalLayerNames = temporalLayerNames;}

	public Scales getScales() {return scales;}
	public void setScales(Scales scales) {this.scales = scales;}

	public String getTimeInfo() {return timeInfo;}
	public void setTimeInfo(String timeInfo) {this.timeInfo = timeInfo;}
}