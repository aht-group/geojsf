package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

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

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.component.entities.OlLayer;
import org.geojsf.component.entities.OlService;
import org.geojsf.event.MapAjaxEvent;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Scales;
import org.geojsf.util.GeoJsfJsLoader;
import org.geojsf.util.component.GeoJsfScalesUtil;
import org.geojsf.xml.gml.Coordinates;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.Map")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Map <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	Gson gson = new Gson();
	
	Hashtable<Long, OlService> serviceList;
	
	// This is taken directly from the value attribute or is constructed from given LAYER components
	private MAP          dmMap;
	private List<String> temporalLayerNames;
	
	// Internal fields
	private Coordinates coords            = new Coordinates();
	private Boolean initStage             = true;
	private Boolean refreshLayersOnUpdate = true;
	private Boolean changedState          = false;
	private Long layerThatChanged         = (long) 0;
	private Scales scales                 = null;
	private String timeInfo               = null;
	private ArrayList<Long> orderedLayers = new ArrayList<Long>();
	
	//Define attributes of the component
	private Integer width  = null;
	private Integer height = 400;
	
	//These are related to the switching of layers
	Hashtable<Long, String>    layerNames;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"OpenLayers.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"scalebar.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJSF.js");
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
/*		if (null == dmMap)
		{
			logger.error("There is no data);
			this.setRendered(false);
			for (UIComponent child : this.getChildren())
			{
				child.setRendered(false);
			}	
		}*/
		if (this.isRendered())
		{
			logger.debug("entering encodebegin");
			try
			{
				if (!containsLayer())
				{
					dmMap = (MAP)getAttributes().get("value");
				}
					else
					{
						MapUtil mapUtil = new MapUtil(ctx);
						dmMap           = (MAP) mapUtil.buildViewsFromLayers(this);
					}
					
					layerNames      = new Hashtable<Long, String>();
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
				renderer.renderDiv(this.getClientId(), MapUtil.buildStyle(height, width));
				renderer.renderLinebreaks(1);
				
				// Check if the list of VIEWs is empty
				if (null == dmMap || dmMap.getViews().isEmpty())
				{
					writer.startElement("center", this);
					writer.writeText("no layers given.", null);
					writer.endElement("center");
					logger.warn("Map can not be rendered without layers!");
				}
				else
				{
					if(true)
					{
						// First - build a list of OpenLayers Services from the list of VIEWs. These are used to render the map layers initially
						serviceList = new Hashtable<Long, OlService>();
						
						// So collect all information from VIEWs
						Hashtable<String, OlService> serviceForUrl = new Hashtable<String, OlService>();
						for (VIEW view : dmMap.getViews())
						{
							LAYER   viewLayer   = view.getLayer();
							SERVICE viewService = view.getLayer().getService();
							
							OlLayer   layer   = new OlLayer();
							layer.setId(viewLayer.getId());
							layer.setName(viewLayer.getCode());
							layer.setVisible(view.isVisible());
							
							if (!serviceForUrl.containsKey(viewService.getWms()))
							{
								OlService service = new OlService();
								service.setUrl(viewService.getWms());
								service.setId(viewService.getId());
								service.setLayerVisibility(new LinkedHashMap<Long, Boolean>());
								service.getLayerVisibility().put(viewLayer.getId(), view.isVisible());
								layerNames.put(viewLayer.getId(), viewLayer.getCode());
								serviceForUrl.put(viewService.getWms(), service);
								orderedLayers.add(service.getId());
								logger.debug("Added layer " +viewLayer.getCode() +" to be " +view.isVisible());
							}
							else
							{
								OlService service = serviceForUrl.get(viewService.getWms());
								layerNames.put(viewLayer.getId(), viewLayer.getCode());
								service.getLayerVisibility().put(viewLayer.getId(), view.isVisible());
							//	orderedLayers.add(service.getId());
								logger.debug("Added layer " +viewLayer.getCode() +" to be " +view.isVisible() +" to existing Service.");
							}
						}
						
						
						// And add all constructed OpenLayers Services to the internal serviceList
						for (String url : serviceForUrl.keySet())
						{
							OlService service = serviceForUrl.get(url);
							serviceList.put(service.getId(), service);
						}
						
						logger.debug("Transfered " +serviceList.size() +" services from " +serviceForUrl.size() +" and the size of orderedLayers is " +orderedLayers.size());
						
						//First, render the JavaScript code to initialize the map
						renderer.renderMapInitialization(this.getFacesContext());
						
						//Next, render the layers
						renderLayers(writer, renderer, false);
				 		
				 		//Set flag that map initiation is completed and not to be repeated
				 		initStage = false;
				 		logger.info("Map initialization completed for " +this.getClientId() +" (ID: " +this.getId() +") holding " +serviceList.size() +" layers.");
				 		writer.endElement("script"); 
					}
				}
		}
	}
	
	public void renderLayers(ResponseWriter writer, JsfRenderUtil renderer, Boolean addScriptTag) throws IOException
	{
		if(addScriptTag){writer.startElement("script", this);}
		
		//First, render the base layer (adding overlays first results in error)
		Long baseLayerId = orderedLayers.get(orderedLayers.size()-1);
		if (orderedLayers.size()==1)
		{
			encodeOlService(serviceList.get(baseLayerId), true, true, writer, renderer);
		}
		else
		{
			encodeOlService(serviceList.get(baseLayerId), true, false, writer, renderer);
		}
		renderer.renderTextWithLB("GeoJSF.eventsRegistered = false;");
		
		//Finally, render the overlay layers
		//for (int i=0;i<serviceList.size()-1;i++)
 		for(int i=orderedLayers.size()-2;i==0;i--) //GEO-64
		{	
 			OlService service = serviceList.get(orderedLayers.get(i));
 			if (i==0)
 			{
 				encodeOlService(service, false, true, writer, renderer);
 			}
 			else
 			{
 				encodeOlService(service, false, false, writer, renderer);
 			}
 			  
		}
 		
 		if(addScriptTag){writer.endElement("script");}
	}
	
	public void encodeOlService(OlService service, Boolean baseLayer, Boolean lastLayer, ResponseWriter writer, JsfRenderUtil renderer) throws IOException
	{
		String sLayers = buildLayerString(service);
		logger.debug("Adding to service " +service.getId() +": "+sLayers);
		renderer.renderTextWithLB("var url            = '" +service.getUrl() +"';");
		renderer.renderTextWithLB("var name           = '" +service.getId() +"';");
		renderer.renderTextWithLB("var params         = {};");
		renderer.renderTextWithLB("params.layers      = '"+sLayers+"';");
		
	/*	ArrayList<String> localTemporalLayer = MapUtil.hasTemporalLayer(service);
		if (localTemporalLayer.size()>0 && null!=timeInfo)
		{
			logger.info("Found time layers in Service " +service.getId() +": " +localTemporalLayer.toString());
		//	temporalLayerNames.addAll(localTemporalLayer);
			temporalLayerNames.add(service.getId() +"");
			renderer.renderTextWithLB("params.time      = '"+timeInfo +"';");
		}
	*/	if (!baseLayer)
		{
			renderer.renderTextWithLB("params.transparent = true;");
			renderer.renderTextWithLB("params.tiled       = true;");
	        renderer.renderTextWithLB("params.tilesorigin = GeoJSF.map.maxExtent.left + ',' + GeoJSF.map.maxExtent.bottom;");
		}
		renderer.renderTextWithLB("params.format      = 'image/png';");
		Hashtable<String,String> parameters = MapUtil.searchSqlViewParameters(this);
		if (parameters.size()>0)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("params.viewparams  = '");
			for (String key : parameters.keySet())
			{
				sb.append(key +":" +parameters.get(key) +";");
			}
			sb.append("';");
		//	renderer.renderTextWithLB(sb.toString());
		}
		
		
	//	renderer.renderTextWithLB("params.makeTheUrlLong      = 'longText';");
		renderer.renderTextWithLB("var options = {};");
		renderer.renderTextWithLB("options.isBaseLayer = " +baseLayer +";");
		renderer.renderTextWithLB("options.singleTile  =  true;");
	//	renderer.renderTextWithLB("options.ratio       =  1;");
		
		// Process scales definition
		Scales scales = MapUtil.searchScale(this); 
		if (scales!=null)
		{
			// Define all allowed levels
			String scaleDefinitions = "options.scales = [";
			GeoJsfScalesUtil scalesUtil = new GeoJsfScalesUtil(scales);
			
			scaleDefinitions += scalesUtil.getScaleList();
			scaleDefinitions += "];" +System.getProperty("line.separator");
			writer.writeText(scaleDefinitions, null);
			
			// Define minimum and maximum values
			writer.writeText("options.maxScale = "+scalesUtil.getMax() +";" +System.getProperty("line.separator"), null);
			writer.writeText("options.minScale = "+scalesUtil.getMin() +";" +System.getProperty("line.separator"), null);
			
			// Set the unit
			writer.writeText("options.units = '" +scales.getUnit() +"';" +System.getProperty("line.separator"), null);
		}
		
	//	writer.writeText("options.scales = [10, 10000, 100000];" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.maxScale = 10;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.minScale = 100000;" +System.getProperty("line.separator"), null);
	//	writer.writeText("options.units = 'm';" +System.getProperty("line.separator"), null);
		renderer.renderTextWithLB("GeoJSF.addLayer(name, url, params, options);");
		renderer.renderLinebreaks(1);
		if (lastLayer)
		{
			renderer.renderTextWithLB("GeoJSF.register(" +service.getId() +");");
		}
	}
	
	
	// --------------------------
	// JSF Decode Phase method
	// --------------------------
	
	public void decode(FacesContext context)
	{
		logger.debug("Current Phase: " +context.getCurrentPhaseId().toString());

		if (this.isRendered())
		{
			java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
			String behaviorEvent = params.get("javax.faces.behavior.event");
		    logger.debug("Handling event of type: " +behaviorEvent +" in decode phase.");
		    
		    // Create a new GeoJsfMap from the given (maybe manipulated) map object
		    try
		    {
		    	dmMap           = (MAP)getAttributes().get("value");
			}
		    catch (Exception e) {e.printStackTrace();}
		    
		    if (null == serviceList)
		    {
		    	logger.error("NO SERVICES?");
		    	return;
		    }
		    logger.debug("Service List has " +serviceList.size() +" entries in decode after restore.");
		    // This will be compared to the values stored in the session before (activate this to check)
	    	// logger.info("Services: " +new LayerSwitchHelper(services, layerNames).toString());
		    
		    // For better reading of the following, some Booleans are defined here
		    
		    Boolean isLayerSwitchEvent = false;
		    Boolean isUpdateMapEvent   = false;
		    Boolean isUpdateModelEvent = false;
		    Boolean isUpdateTimeEvent  = false;
		    Boolean isUpdateParamsEvent= false;
		    Boolean isMapClickEvent    = false;
		    Boolean isMapMoveEvent     = false;
		    
		    if (null!=behaviorEvent)
		    {
		    	isLayerSwitchEvent = behaviorEvent.equals("layerSwitch");
			    isUpdateMapEvent   = behaviorEvent.equals("updateMap");
			    isUpdateModelEvent = behaviorEvent.equals("updateModel");
			    isUpdateTimeEvent  = behaviorEvent.equals("updateTime");
			    isUpdateParamsEvent= behaviorEvent.equals("updateParams");
			    isMapClickEvent    = behaviorEvent.equals("mapClick");
			    isMapMoveEvent     = behaviorEvent.equals("mapMove");
		    }
		    
		    //if (null!=services && ((null != behaviorEvent && !isLayerSwitchEvent) || null==behaviorEvent || isUpdateMapEvent))
		    if (null!=serviceList)
			{
		    	// Load the (maybe updated) SqlViewParameters
		    	logger.debug("Searching for SQLVP in Decode");
		    	Hashtable<String,String> parameters = MapUtil.searchSqlViewParameters(this);
		    	StringBuffer sb = new StringBuffer();
				if (parameters.size()>0)
				{
					sb.append("'");
					for (String key : parameters.keySet())
					{
						sb.append(key +":" +parameters.get(key) +";");
					}
					sb.append("';");
				}
				
				// Iterate through all Views (that hold the information if a Layer is visible)
		    	for (VIEW view : dmMap.getViews())
				{
					Long serviceId = view.getLayer().getService().getId();
					Long layerId   = view.getLayer().getId();
					
					// Check the visibility value before and currently
					Boolean before    = (Boolean) serviceList.get(serviceId).isLayerVisible(layerId);
					Boolean now       = view.isVisible();
					logger.trace("Checking " +dmMap.getViews().size() +"of dmMap for changes.");
					logger.trace("Checking layer " +view.getLayer().getCode());
					
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
						
						logger.debug("Layer " +layerThatChanged +" changed to " +changedState);
					}
				}
			}
			
			// Handling of mapClick event fired by JavaScript API
	        if (null!= behaviorEvent && isMapClickEvent)
	        {
	        	java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
	     		if (behaviors.isEmpty())
	     		{
	     			logger.debug("no behaviors.exiting.");
	     			return;
	     		}
	            List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);
	            if (behaviors.size() > 0)
	            {
	            	String behaviorSource = params.get("javax.faces.source");
	            	String clientId = getClientId(context);
	            	if (behaviorSource != null && null!= behaviorsForEvent && behaviorSource.equals(clientId))
	            	{
	            		for (ClientBehavior behavior: behaviorsForEvent)
	            		{
	            			logger.trace("Found " +behavior.getClass().toString());
	            			MapAjaxEvent ajaxEvent = new MapAjaxEvent(this, behavior);
	            			
	            			logger.trace("Setting CLiCK ");
	            			ajaxEvent.setClickCoordinates(params);
	            			ajaxEvent.setViewport(params);
	            			
	            			behavior.broadcast(ajaxEvent);
	            		}
	            	}
	            }
	        }
	        
	     // Handling of mapMove event fired by JavaScript API
	        if (null!= behaviorEvent && isMapMoveEvent)
	        {
	        	java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
	     		if (behaviors.isEmpty())
	     		{
	     			logger.error("no behaviors.exiting.");
	     			return;
	     		}
	            List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);
	            
	            if (null != behaviorsForEvent)
	            {
	            	String behaviorSource = params.get("javax.faces.source");
	            	String clientId = getClientId(context);
	            	if (behaviorSource != null && behaviorSource.equals(clientId))
	            	{
	            		for (ClientBehavior behavior: behaviorsForEvent)
	            		{
	            			logger.trace("Found " +behavior.getClass().toString());
	            			MapAjaxEvent ajaxEvent = new MapAjaxEvent(this, behavior);
	            			ajaxEvent.setViewport(params);
	            			behavior.broadcast(ajaxEvent);
	            		}
	            	}
	            }
	        }
	        
	        // Handling of mapClick event fired by JavaScript API
	        if (null!= behaviorEvent && isUpdateTimeEvent)
	        {
	        	logger.info("Received updateTime event from JavaScript API.");
	        	
	        	// Read current time from request
	        	// Time is given in ms format as String
				String timeString        = params.get("org.geojsf.update.time");
				Long   time              = Long.parseLong(timeString);
				
				MapUtil.updateTime(this, time);
				
	        }
	        
	        if (null!=serviceList && isUpdateMapEvent)
	        {
	        	try{
	        	logger.trace("State Problem?");
	        	logger.debug("Looking for the changed layer: " +layerThatChanged +" now in state " +changedState);
	        	VIEW view = null;
	        	for (VIEW viewIterated : dmMap.getViews())
				{
					if (viewIterated.getLayer().getId() == layerThatChanged)
					{
						view = viewIterated;
						logger.trace("Found view to be changed: " +view.getLayer().getCode());
					}
				}
	        	
	        	// If there is a new value, generate a command to toggle the assigned service in OpenLayers using hide/show/merge
				logger.info("Changing visibility of layer (" +view.getLayer().getId() +") " +view.getLayer().getCode() +" of Service " +view.getLayer().getService().getId() +" to " +changedState);

				String toggleCommand = toggleLayer(view.getLayer().getService().getId(), view.getLayer().getId(), view.isVisible());
				
				// Now send the command (JSON-String representation of the Command object) to the client using PrimeFaces CallbackParam methodology
				// This can be used in the oncomplete AJAX-callback JavaScript function like performLayerSwitch(xhr, status, args)
				logger.debug("Sending layer switch command to JavaScript client logic: " +toggleCommand +" to switch layer " +view.getLayer().getId() +" of service " +view.getLayer().getService().getId() +" to " +view.isVisible());
				RequestContext.getCurrentInstance().addCallbackParam("toggleLayer", toggleCommand);
	        	} catch (Exception e)
	        	{
	        		logger.error(e.getMessage());
	        	}
				
	        }
		}
	}
	
	public String toggleLayer(Long serviceId, Long layerId, Boolean active)
	{
		Order order       = new Order();
		String command    = null;
		OlService service = serviceList.get(serviceId);
		
		// The following code is building the list of layers that are to be shown after the command is executed
		// First, set the requested layer to the requested status
		service.setVisibility(layerId, active);
		
		// Then process the complete list of layers assigned to the requested layers service
		ArrayList<String> activeLayers = new ArrayList<String>();
		for (Long layerOfService : service.getLayerVisibility().keySet())
		{
			Boolean visible  = service.isLayerVisible(layerOfService);
			if (visible) {activeLayers.add(layerNames.get(layerOfService));}
		}
	
		// Use MERGE command as standard
		command = "merge";
		
		// Now let's see if there is another option that is more appropriate
		//If the only layer active is the one that should be hidden, hide the whole service
		if (activeLayers.size()==0 && !active && service.getLayerVisibility().size()==1)
		{command = "hide";}
		
		//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
		if (active && activeLayers.size()==1 && service.getLayerVisibility().size()==1)
		{command = "show";}

		//This is obsolete since it is covered indirectly by the code above
		//If there are other active layers, merge the newly constructed service
		/*		if (activeLayers.size()>1 && !active && service.getLayer().size()>1)
				{command = "merge";}
				
				//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
				if (active && activeLayers.isEmpty() && service.getLayer().size()>1)
				{command = "merge";}
		*/
		
		// To have the correct layer allignement for OpenLayers, reverse the list order
		Collections.reverse(activeLayers);
		
		// Create the layer switch command for JavaScript GeoJSF logic
		order.setCommand(command);
		order.setServiceId(serviceId);
		order.setLayer(activeLayers);
		return gson.toJson(order);
	}
	
	
	// -------------------------------------------
	// JSF Methods for State Saving and Event Name
	// -------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public void restoreState(FacesContext context, Object state)
	{
		if (this.isRendered())
		{
			Object[] storedState = (Object[]) state;
		    logger.debug("Restoring state.");
			try {
			initStage        = (Boolean) storedState[0];
			serviceList      = (Hashtable<Long, OlService>) storedState[1];
			layerNames       = (Hashtable<Long, String>) storedState[3];
		    dmMap            = (MAP) storedState[4];
			layerThatChanged = (Long) storedState[5];
			changedState     = (Boolean) storedState[6];
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
		    rtrn[1] = serviceList;
		    rtrn[3] = layerNames;
		    rtrn[4] = dmMap;
		    rtrn[5] = layerThatChanged;
		    rtrn[6] = changedState;
		}
		return rtrn;
	}
	
	@Override
	public Collection<String> getEventNames()
	{
		ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		events.add("mapMove");
		events.add("updateModel");
		events.add("updateParams");
		return events;
	}

	@Override
	public String getDefaultEventName() {return "mapClick";}
	
	public String buildLayerString(OlService service)
	{
		StringBuffer sb = new StringBuffer();
		List<Long> list = new ArrayList<Long>();
		list.addAll(service.getLayerVisibility().keySet());
		Collections.reverse(list);
		for (Long i : list)
		{
			if (service.getLayerVisibility().get(i))
			{
				sb.append(layerNames.get(i) +",");
			}
		}
		if (sb.length()>0)
		{
			sb.deleteCharAt(sb.length()-1);
		}
	return sb.toString();
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
	
	// --------------------------
	// Standard Getter and Setter
	// --------------------------
	
	public Integer getWidth(){return width;}
	public void setWidth(Integer width) {this.width = width;}
	
	public Integer getHeight() {return height;}
	public void setHeight(Integer height) {this.height = height;}
	
	public Coordinates getCoords() {return coords;}
	public void setCoords(Coordinates coords) {this.coords = coords;}

	public Hashtable<Long, OlService> getServiceList() {return serviceList;}
	public void setServiceList(Hashtable<Long, OlService> serviceList) {this.serviceList = serviceList;}

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