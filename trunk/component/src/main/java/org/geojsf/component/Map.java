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
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.component.LayerSwitchHelper.Service;
import org.geojsf.event.MapAjaxEvent;
import org.geojsf.exception.UnconsistentConfgurationException;
import org.geojsf.factory.txt.TxtOpenlayersLayerFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.util.GeoJsfJsLoader;
import org.geojsf.xml.geojsf.Scales;
import org.geojsf.xml.gml.Coordinates;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.Map")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Map <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>> 
	extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private List<SERVICE> serviceList;
	private List<String> temporalLayerNames;
	private MAP dmMap;
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private Integer width = null;
	private Integer height = null;
	private String timeInfo = null;
	private Scales scales = null;
	private Boolean initStage = true;
	private Boolean refreshLayersOnUpdate = true;
	
	//These are related to the switching of layers
	private LayerSwitchHelper helper;
	Hashtable<String, Service> services;
	Hashtable<String, String>  layerNames;
	
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
	
	
	// --------------------------
	// JSF Encode Phase methods
	// --------------------------

	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		try
		{
			if (true) 
			{
				// TODO: Really a new instance required, or should it be in the constructor?
				MapUtil<L,D,SERVICE,LAYER,MAP,VIEW> util = new MapUtil<L,D,SERVICE,LAYER,MAP,VIEW>(ctx);
				dmMap = util.initLayerConfiguration(this);
				for (VIEW view : dmMap.getViews())
				{
					view.setVisible(true);
				}
			}
		}
		catch (UnconsistentConfgurationException e)
		{
			logger.warn("Problem occured when processing layers: " +e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			logger.warn("Problem occured when processing layers: " +e.getMessage());
			e.printStackTrace();
			this.setRendered(false);
		}
		
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
				logger.warn("Map can not be rendered without layers!");
			}
			else
			{
				if(!refreshLayersOnUpdate && initStage || refreshLayersOnUpdate)
				{
					LayerSwitchHelper helper = new LayerSwitchHelper(this.getServiceList());
					services   = helper.getServices();
					layerNames = helper.getLayerNames();
					
					//First, render the JavaScript code to initialize the map
					renderer.renderMapInitialization(this.getFacesContext());
					
					//Next, render the layers
					renderLayers(writer, renderer, false);
			 		
			 		//Set flag that map initiation is completed and not to be repeated
			 		initStage = false;
			 		logger.info("Map initialization completed for " +this.getClientId() +" (ID: " +this.getId() +")");
			 		writer.endElement("script"); 
				}
			}
		}
	}
	
	public void renderLayers(ResponseWriter writer, JsfRenderUtil renderer, Boolean addScriptTag) throws IOException
	{
		if(addScriptTag){writer.startElement("script", this);}
		
		//First, render the base layer (adding overlays first results in error)
		SERVICE baseLayer = serviceList.get(serviceList.size()-1);
		encodeLayer(baseLayer, true, writer, renderer);
		
		//Finally, render the overlay layers
		//for (int i=0;i<serviceList.size()-1;i++)
 		for(int i=serviceList.size()-2;i==0;i--) //GEO-64
		{	
 			SERVICE service = serviceList.get(i);
 			encodeLayer(service, false, writer, renderer);  
		}
 		
 		if(addScriptTag){writer.endElement("script");}
	}
	
	public void encodeLayer(SERVICE service, Boolean baseLayer, ResponseWriter writer, JsfRenderUtil renderer) throws IOException
	{
		String sLayers = TxtOpenlayersLayerFactory.buildLayerString(service);
//		String sLayers = "x";
		logger.info("Adding "+service.getCode()+": "+sLayers);
		renderer.renderTextWithLB("var url    = '" +service.getUrl() +"';");
		renderer.renderTextWithLB("var name   = '" +service.getId() +"';");
		renderer.renderTextWithLB("var params = {};");
		renderer.renderTextWithLB("params.layers      = '"+sLayers+"';");
		ArrayList<String> localTemporalLayer = MapUtil.hasTemporalLayer(service);
		if (localTemporalLayer.size()>0 && null!=timeInfo)
		{
			logger.info("Found time layers in Service " +service.getId() +": " +localTemporalLayer.toString());
		//	temporalLayerNames.addAll(localTemporalLayer);
			temporalLayerNames.add(service.getId() +"");
			renderer.renderTextWithLB("params.time      = '"+timeInfo +"';");
		}
		
		renderer.renderTextWithLB("params.transparent = true;");
		renderer.renderTextWithLB("params.format      = 'image/png';");
	//	renderer.renderTextWithLB("params.makeTheUrlLong      = 'longText';");
		renderer.renderTextWithLB("var options = {};");
		renderer.renderTextWithLB("options.isBaseLayer = " +baseLayer +";");
		
	//	writer.writeText("options.scales = [10, 10000, 100000];" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.maxScale = 10;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.minScale = 100000;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.units = 'm';" +System.getProperty("line.separator"), null);
		renderer.renderTextWithLB("GeoJSF.addLayer(name, url, params, options);");
		renderer.renderLinebreaks(1);
	}
	
	
	// --------------------------
	// JSF Decode Phase method
	// --------------------------
	
	public void decode(FacesContext context)
	{
		java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");
	    logger.info("Handling event of type: " +behaviorEvent +" in decode phase.");
	    
	    // Create a new GeoJsfMap from the given (maybe manipulated) map object
	    try
	    {
	    	MapUtil<L,D,SERVICE,LAYER,MAP,VIEW> util = new MapUtil<L,D,SERVICE,LAYER,MAP,VIEW>(context);
			dmMap = util.initLayerConfiguration(this);
		}
	    catch (Exception e) {e.printStackTrace();}
	    
	    // This will be compared to the values stored in the session before (activate this to check)
    	// logger.info("Services: " +new LayerSwitchHelper(services, layerNames).toString());
    	
		if (null!=services)
		{
			// Iterate through all Views (that hold the information if a Layer is visible)
			for (VIEW view : dmMap.getViews())
			{
				Integer serviceId = (int) view.getLayer().getService().getId();
				Integer layerId   = (int) view.getLayer().getId();
				
				// Check the visibility value before and currently
				Boolean before    = services.get(serviceId +"").getLayer().get(layerId +"");
				Boolean now       = view.isVisible();
				
				if (before.equals(now))
				{
					// If there is no change, do nothing
					// logger.info("Layer " +view.getLayer().getCode() +" did not change it's visibility. Still " +view.isVisible());
				}
				else
				{
					// If there is a new value, generate a command to toggle the assigned service in OpenLayers using hide/show/merge
					logger.info("Trying to change (" +view.getLayer().getId() +") " +view.getLayer().getCode() +" of Service " +view.getLayer().getService().getId() +" from "+before +" to " +now);

					// Restore the LayerSwitchHelper with the information saved in the run before
					// (see save/restore methods)
					helper = new LayerSwitchHelper(services, layerNames);

					// Generate the command using the LayerSwitchHelper 
					// logger.info("Current LayerSwitchHelper content: " +helper.toString());
					String toggleCommand = helper.toggleLayer(view.getLayer().getId() +"");
					
					// Get the new service Hashtable so it will be saved as a reference for the current state for the next run
					services = helper.getServices();
					
					// Now send the command (JSON-String representation of the Command object) to the client using PrimeFaces CallbackParam methodology
					// This can be used in the oncomplete AJAX-callback JavaScript function like performLayerSwitch(xhr, status, args)
					logger.info("Sending layer switch command to JavaScript client logic: " +toggleCommand +" to switch layer " +view.getLayer().getId() +" of service " +view.getLayer().getService().getId() +" to " +view.isVisible());
					RequestContext.getCurrentInstance().addCallbackParam("toggleLayer", toggleCommand);
				}
			}
		}
		
		// Handling of mapClick event fired by JavaScript API
        if (null!= behaviorEvent && behaviorEvent.equals("mapClick"))
        {
        	java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
     		if (behaviors.isEmpty())
     		{
     			logger.error("no behaviors.exiting.");
     			return;
     		}
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
            		}
            	}
            }
        }
        
        // Handling of layerSwitch event fired by JavaScript API
        if (null!= behaviorEvent && behaviorEvent.equals("layerSwitch"))
		{
        	logger.info("Received layerSwitch event from JavaScript API.");
        	
        	// Read information from request
        	// The service ID and the state are calculated from the data and current state now
        	// String serviceIdParam = params.get("org.geojsf.switch.service");
        	// Boolean active        = new Boolean(params.get("org.geojsf.switch.on"));
			String layerId        = params.get("org.geojsf.switch.layer");
			
			// Restore the LayerSwitchHelper with current state and information
			helper = new LayerSwitchHelper(services, layerNames);
			// logger.debug("Current LayerSwitchHelper content: " +helper.toString());
			
			// Generate command to switch layer in OpenLayers using GeoJSF JavaScript API
			String toggleCommand = helper.toggleLayer(layerId);
			
			// Get the state to be saved in session
			services = helper.getServices();
			
			logger.info("Sending layer switch command to JavaScript client logic: " +toggleCommand);
			RequestContext.getCurrentInstance().addCallbackParam("toggleLayer", toggleCommand);
		}
	}
	
	
	// -------------------------------------------
	// JSF Methods for State Saving and Event Name
	// -------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public void restoreState(FacesContext context, Object state)
	{
	    Object[] storedState = (Object[]) state;
	    logger.info("Restoring state.");
		serviceList = (List<SERVICE>) storedState[1];
		initStage   = (Boolean) storedState[0];
		services    = (Hashtable<String, Service>) storedState[2];
	    layerNames  = (Hashtable<String, String>) storedState[3];
	    dmMap       = (MAP) storedState[4];
		helper      = new LayerSwitchHelper(services, layerNames);
		logger.debug("Current LayerSwitchHelper content: " +helper.toString());
	}
	
	@Override
	public Object saveState(FacesContext context)
	{
	    Object[] rtrn = new Object[5];
	    rtrn[0] = initStage;
	    rtrn[1] = serviceList;
	    rtrn[2] = services;
	    rtrn[3] = layerNames;
	    rtrn[4] = dmMap;
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

	public List<SERVICE> getServiceList() {return serviceList;}
	public void setServiceList(List<SERVICE> serviceList) {this.serviceList = serviceList;}

	public List<String> getTemporalLayerNames() {return temporalLayerNames;}
	public void setTemporalLayerNames(List<String> temporalLayerNames) {this.temporalLayerNames = temporalLayerNames;}

	public Scales getScales() {return scales;}
	public void setScales(Scales scales) {this.scales = scales;}

	public String getTimeInfo() {return timeInfo;}
	public void setTimeInfo(String timeInfo) {this.timeInfo = timeInfo;}

	public Boolean getInitStage() {return initStage;}
	public void setInitStage(Boolean initStage) {this.initStage = initStage;}

	public Boolean getRefreshLayersOnUpdate() {return refreshLayersOnUpdate;}
	public void setRefreshLayersOnUpdate(Boolean refreshLayersOnUpdate) {this.refreshLayersOnUpdate = refreshLayersOnUpdate;}

	public MAP getDmMap() {return dmMap;}
	public void setDmMap(MAP dmMap) {this.dmMap = dmMap;}
}