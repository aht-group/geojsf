package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
	
	private Coordinates coords = new Coordinates();
	
	//Define attributes of the component
	private Integer width = 300;
	private Integer height = 400;
	private String timeInfo = null;
	private Scales scales = null;
	private Boolean initStage = true;
	private Boolean refreshLayersOnUpdate = true;
	
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
			if (true) {MapUtil.initLayerConfiguration(this);}
		}
		catch (UnconsistentConfgurationException e)
		{
			logger.warn("Problem occured when processing layers: " +e.getMessage());
		}
		catch (Exception e)
		{
			logger.warn("Problem occured when processing layers: " +e.getMessage());
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
			temporalLayerNames.addAll(localTemporalLayer);
			renderer.renderTextWithLB("params.time      = '"+timeInfo +"';");
		}
		
		renderer.renderTextWithLB("params.transparent = true;");
		renderer.renderTextWithLB("params.format      = 'image/png';");
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
            		}
            	}
            }
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
	}
	
	@Override
	public Object saveState(FacesContext context)
	{
	    Object[] rtrn = new Object[2];
	    rtrn[0] = initStage;
	    rtrn[1] = serviceList;
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
}