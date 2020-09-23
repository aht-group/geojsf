package org.geojsf.component;

import java.util.Map;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import org.geojsf.event.MapAjaxEvent;
import org.geojsf.helpers.LayerManager;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHandler {
    
    final static Logger logger = LoggerFactory.getLogger(EventHandler.class);
    
    private UIComponent		component;
    private CoordinatePicker	mapComponent;
    private FacesContext		context;
    private GeoJsfMap		dmMap;
    private LayerManager		layerManager;
    private Boolean		changedState	    = false;
    private Long			layerThatChanged    = (long) 0;
    
    // Init the switches
    private Boolean isUpdateMapEvent   = false;
    private Boolean isUpdateTimeEvent  = false;
    private Boolean isMapClickEvent    = false;
    private Boolean isMapMoveEvent     = false;
    private Boolean isMarkerMoveEvent  = false;
	
    
    public EventHandler(CoordinatePicker mapComponent, FacesContext context)
    {
	this.component	    = mapComponent;
	this.mapComponent   = mapComponent;
	this.context	    = context;
	this.dmMap	    = mapComponent.getDmMap();
	this.layerManager   = mapComponent.layerManager;
	
	// In the request parameters, all information and actual data payload is included such as clicked coordinates etc.
	// The info is identfified by keys such as:
	// javax.faces.behavior.event	    for the event name or
	// org.geojsf.viewport.center.lon    for the longitude part of coordiantes returned from JavaScript
	Map<String,String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
	
	// Extract the behavior name
	String behaviorEvent = requestParameterMap.get("javax.faces.behavior.event");
	logger.info("Handling event of type: " +behaviorEvent +" in decode phase.");
	
	// Set what event we have to handle
	if (null!=behaviorEvent)
	{
	    isUpdateMapEvent   = behaviorEvent.equals("updateMap");
	    isUpdateTimeEvent  = behaviorEvent.equals("updateTime");
	    isMapClickEvent    = behaviorEvent.equals("mapClick");
	    isMapMoveEvent     = behaviorEvent.equals("mapMove");
	    isMarkerMoveEvent  = behaviorEvent.equals("markerMove");
	}
	
	// First lets see if there are layers visibility state changed compared to before
	checkForChangedLayerConfiguration(requestParameterMap);
	
	// Check if there are behaviors associated and if not cancel the handling
	Map<String, List<ClientBehavior>> behaviors = mapComponent.getClientBehaviors();
	if (behaviors.isEmpty())
	{
	    logger.debug("no behaviors.exiting.");
	    return;
	}
	else
	{
	    String behaviorSource   = requestParameterMap.get("javax.faces.source");
	    String clientId	    = mapComponent.getClientId(context);
	    List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

	    if (behaviorSource != null && null!= behaviorsForEvent && behaviorSource.equals(clientId))
	    {
		// For events that pass the viewport state and coordinates associated with the event
		// (where has the user clicked? what is the new center after map move? where has the marker been moved to?)
		if (isMapMoveEvent || isMapClickEvent || isMarkerMoveEvent)
		for (ClientBehavior behavior: behaviorsForEvent)
		{
		    logger.trace("Found " +behavior.getClass().toString());
		    MapAjaxEvent ajaxEvent = new MapAjaxEvent(mapComponent, behavior);

		    logger.trace("Setting CLiCK ");
		    ajaxEvent.setClickCoordinates(requestParameterMap);
		    ajaxEvent.setViewport(requestParameterMap);

		    behavior.broadcast(ajaxEvent);
		}
	    }
		if (isUpdateMapEvent) // && layerManager.getServiceList()!=null)
		{
		    try
		    {
			logger.trace("State Problem?");
			logger.info("Looking for the changed layer: " +layerThatChanged +" now in state " +changedState);
			GeoJsfView view = null;
			for (Object iteratedView : dmMap.getViews())
			{
			    GeoJsfView viewIterated = (GeoJsfView) iteratedView;
			    if (viewIterated.getLayer().getId() == layerThatChanged)
			    {
				view = viewIterated;
				logger.trace("Found view to be changed: " +view.getLayer().getCode());
			    }
			}

			// If there is a new value, generate a command to toggle the assigned service in OpenLayers using hide/show/merge
			logger.info("Changing visibility of layer (" +view.getLayer().getId() +") " +view.getLayer().getCode() +" of Service " +view.getLayer().getService().getId() +" to " +changedState);

			String toggleCommand = layerManager.toggleLayer(view.getLayer().getService().getId(), view.getLayer().getId(), view.isVisible());

			// Now send the command (JSON-String representation of the Command object) to the client using PrimeFaces CallbackParam methodology
			// This can be used in the oncomplete AJAX-callback JavaScript function like performLayerSwitch(xhr, status, args)
			logger.debug("Sending layer switch command to JavaScript client logic: " +toggleCommand +" to switch layer " +view.getLayer().getId() +" of service " +view.getLayer().getService().getId() +" to " +view.isVisible());
			RequestContext.getCurrentInstance().addCallbackParam("toggleLayer", toggleCommand);
		    } catch (Exception e)
		    {
			logger.error(e.getMessage());
		    }
		}
		
		// The update time event is important when WMS-T based maps are to be rendered
		if (isUpdateTimeEvent)
	        {
		    logger.info("Received updateTime event from JavaScript API.");

		    // Read current time from request
		    // Time is given in ms format as String
		    String timeString        = requestParameterMap.get("org.geojsf.update.time");
		    Long   time              = Long.parseLong(timeString);

		    MapUtil.updateTime(mapComponent, time);
	        }
	    
	}
    }
    
    
    public void checkForChangedLayerConfiguration(Map<String,String> params)
    {	   
	logger.info("Checking for changed Layer configuration");
	if (null!=layerManager.getServiceList() && mapComponent.getDmMap()!=null && mapComponent.getDmMap().getViews()!=null)
	{
	    // Iterate through all Views (that hold the information if a Layer is visible)
	    for (Object o : dmMap.getViews())
	    {
		GeoJsfView view = (GeoJsfView) o;
		Long serviceId = view.getLayer().getService().getId();
		Long layerId   = view.getLayer().getId();

		// Check the visibility value before and currently
		Boolean before    = (Boolean) layerManager.getServiceList().get(serviceId).isLayerVisible(layerId);
		Boolean now       = view.isVisible();
		logger.info("Checking " +dmMap.getViews().size() +"of dmMap for changes.");
		logger.info("Checking layer " +view.getLayer().getCode());

		if (before.equals(now))
		{
		    // If there is no change, do nothing
		     logger.debug("Layer " +view.getLayer().getCode() +" did not change it's visibility. Still " +view.isVisible());
		}
		else
		{
		    // In case the stored state and the current state of a view (= layer visibility) is different, prepare updating the map
		    changedState     = now;
		    layerThatChanged = view.getLayer().getId();

		    logger.info("Layer " +layerThatChanged +" changed to " +changedState);
		}
	    }
	}   
    }

    public Boolean getChangedState() {return changedState;} public void setChangedState(Boolean changedState) {this.changedState = changedState;}
    public Long getLayerThatChanged() {return layerThatChanged;} public void setLayerThatChanged(Long layerThatChanged) {this.layerThatChanged = layerThatChanged;} 
}
