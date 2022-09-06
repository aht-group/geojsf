package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.model.component.OlService;
import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.model.xml.geojsf.Scales;
import org.geojsf.model.xml.specs.gml.Coordinates;
import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.geojsf.helpers.LayerManager;

@ResourceDependencies({
@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
@ResourceDependency(library = "geoJsfCss"  , name = "ol.css", target = "head"),
@ResourceDependency(library = "js.geojsf", name = "geojsf.css", target = "head"),
@ResourceDependency(library = "js.geojsf", name = "ol.js", target = "head"),
@ResourceDependency(library = "js.geojsf", name = "GeoJSF3.js", target = "head")})
@FacesComponent(value="org.geojsf.component.CoordinatePicker")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class CoordinatePicker 
	extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(CoordinatePicker.class);
	
	// Tools and Helpers
	protected LayerManager layerManager = new LayerManager();
	protected EventHandler eventHandler = new EventHandler(this, this.getFacesContext());
	
	// This is taken directly from the value attribute or is constructed from given LAYER components
	private GeoJsfMap    dmMap;
	private List<String> temporalLayerNames;
	private String       baseMap; 
	
	// Internal fields
	private Coordinates coords            = new Coordinates();
	private Coordinate  markerPosition    = null;
	private String markerUrl		     = null;
	private Boolean initStage             = true;
	private Boolean refreshLayersOnUpdate = true;
	private Scales scales                 = null;
	private String timeInfo               = null;
	
	//Define attributes of the component
	private Integer width  = null;
	private Integer height = 400;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
	    if(event instanceof PostAddToViewEvent)
	    {
		GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"ol-debug.js");
		GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJSF3.js");
	    }
	    else
	    {
		logger.debug("received ComponentSystemEvent: " +event.getClass().getName());
	    }
	    super.processEvent(event);
	}
	
	
	// --------------------------
	// JSF Encode Phase methods
	// --------------------------

	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
	    logger.info("Is Map in initStage?" +initStage);
	    if (this.isRendered())
	    {
		logger.debug("entering encodebegin");
		try
		{
		    markerPosition = (Coordinate)getAttributes().get("markerPosition");
		    if(markerPosition!=null)
		    {
			logger.info("Marker initially at " +markerPosition.getLat() + "," +markerPosition.getLon());
		    }
		    else
		    {
			logger.warn("Could not read marker position");
		    }
		    markerUrl = (String)getAttributes().get("markerUrl");
		    if(markerUrl!=null)
		    {
			logger.info("Marker image to be found at " +markerUrl);
		    }
		    else
		    {
			logger.warn("Could not read marker source url");
		    }

		    if (!MapUtil.containsLayer(this))
		    {
			dmMap = (GeoJsfMap)getAttributes().get("value");
		    }
		    else
		    {
			MapUtil mapUtil = new MapUtil(ctx);
			dmMap           = (GeoJsfMap) mapUtil.buildViewsFromLayers(this);
		    }

		}
		catch (Exception e)
		{
		    logger.warn("Problem occured when processing layers: " +e.getMessage());
		    e.printStackTrace();
		    this.setRendered(false);
		}

		ResponseWriter writer = ctx.getResponseWriter();
		JsfRenderUtil renderer = new JsfRenderUtil(writer, this);
		renderer.renderLinebreaks(2);

		// Render the DIV container that will be used by OpenLayers to inject the OpenLayers map
		renderer.renderDiv(this.getClientId(), MapUtil.buildStyle(height, width),"");
		//renderer.renderDiv(this.getClientId()+"_export-png", MapUtil.buildStyle(height, width),"PNG");
		renderer.renderLinebreaks(1);

		//First, render the JavaScript code to initialize the map
		renderer.renderMapInitialization(this.getFacesContext());

		
		// Check if the list of VIEWs is empty
		if (null == dmMap || dmMap.getViews().isEmpty())
		{
		    //writer.startElement("center", this);
		    //writer.writeText("no layers given.", null);
		    //writer.endElement("center");
		    logger.warn("Map can not be rendered without layers!");
		}
		else
		{
		    layerManager = new LayerManager();
		    layerManager.initLayers(dmMap);

		    
		    //Next, render the layers
		    layerManager.renderLayers(writer, renderer, false);
		}
		//logger.info("Map initialization completed for " +this.getClientId() +" (ID: " +this.getId() +") holding " +serviceList.size() +" layers.");
		writer.endElement("script"); 

		//Set flag that map initiation is completed and not to be repeated
		initStage = false;
	    }
	}
	
	// --------------------------
	// JSF Decode Phase method
	// --------------------------
	
	@Override
	public void decode(FacesContext context)
	{
	    logger.debug("Current Phase: " +context.getCurrentPhaseId().toString());
	    
	    // The event handler is checking for client behaviors and handles the events if present
	    eventHandler = new EventHandler(this, context);
	    
	    if (this.isRendered())
	    {
		// Create a new GeoJsfMap from the given (maybe manipulated) map object
		try
		{
		    dmMap           = (GeoJsfMap)getAttributes().get("value");
		}
		catch (Exception e) {e.printStackTrace();}

		if (null == layerManager.getServiceList())
		{
		    logger.error("NO SERVICES?");
		    return;
		}
		
		logger.debug("Service List has " +layerManager.getServiceList().size() +" entries in decode after restore.");
	    }
	}
	
	// -------------------------------------------
	// JSF Methods for State Saving and Event Name
	// -------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public void restoreState(FacesContext context, Object state)
	{
	    if(this.isRendered())
	    {
		Object[] storedState = (Object[]) state;
		logger.debug("Restoring state.");
		layerManager = new LayerManager();
		try
		{
		    initStage        = (Boolean) storedState[0];
		    layerManager.setServiceList((Hashtable<Long, OlService>) storedState[1]);
		    layerManager.setLayerNames((Hashtable<Long, String>) storedState[3]);
		    dmMap            = (GeoJsfMap) storedState[4];
		    if (eventHandler!=null)
		    {
			eventHandler.setLayerThatChanged((Long) storedState[5]);
			eventHandler.setChangedState((Boolean) storedState[6]);
		    }
		    
		}
		catch (Exception e)
		{
		    logger.error("Exception when restoring: " +e.getMessage());
		}
	    }
	}
	
	@Override
	public Object saveState(FacesContext context)
	{
	    Object[] rtrn = new Object[7];
	    if (this.isRendered())
	    {
		rtrn[0] = initStage;
		rtrn[1] = layerManager.getServiceList();
		rtrn[3] = layerManager.getLayerNames();
		rtrn[4] = dmMap;
		
		if (eventHandler!=null)
		{
		    rtrn[5] = eventHandler.getLayerThatChanged();
		    rtrn[6] = eventHandler.getChangedState();
		}
	    }
	    return rtrn;
	}
	
	@Override
	public Collection<String> getEventNames()
	{
	    ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		events.add("mapMove");
		events.add("markerMove");
		events.add("updateModel");
		events.add("updateParams");
	    return events;
	}

	@Override
	public String getDefaultEventName() {return "mapClick";}
	
	// --------------------------
	// Standard Getter and Setter
	// --------------------------
	public String getBaseMap() {return baseMap;} public void setBaseMap(String baseMap) {this.baseMap = baseMap;}
	public Integer getWidth(){return width;} public void setWidth(Integer width) {this.width = width;}
	public Integer getHeight() {return height;} public void setHeight(Integer height) {this.height = height;}
	public Coordinates getCoords() {return coords;} public void setCoords(Coordinates coords) {this.coords = coords;}
	public List<String> getTemporalLayerNames() {return temporalLayerNames;} public void setTemporalLayerNames(List<String> temporalLayerNames) {this.temporalLayerNames = temporalLayerNames;}
	public Scales getScales() {return scales;} public void setScales(Scales scales) {this.scales = scales;}
	public String getTimeInfo() {return timeInfo;} public void setTimeInfo(String timeInfo) {this.timeInfo = timeInfo;}
	public Boolean getInitStage() {return initStage;} public void setInitStage(Boolean initStage) {this.initStage = initStage;}
	public Boolean getRefreshLayersOnUpdate() {return refreshLayersOnUpdate;} public void setRefreshLayersOnUpdate(Boolean refreshLayersOnUpdate) {this.refreshLayersOnUpdate = refreshLayersOnUpdate;}
	public GeoJsfMap getDmMap() {return dmMap;} public void setDmMap(GeoJsfMap dmMap) {this.dmMap = dmMap;}
	public Coordinate getMarkerPosition() {return markerPosition;} public void setMarkerPosition(Coordinate markerPosition) {this.markerPosition = markerPosition;}
	public String getMarkerUrl() {return markerUrl;} public void setMarkerUrl(String markerUrl) {this.markerUrl = markerUrl;}
}