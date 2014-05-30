package org.geojsf.component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UISelectMany;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.component.LayerSwitchHelper.Service;
import org.geojsf.controller.util.GeoJsfMapHelper;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "ios.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "ios.css", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.LayerSwitcher")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class LayerSwitcher extends UIComponentBase implements ClientBehaviorHolder{
	
	final static Logger logger = LoggerFactory.getLogger(LayerSwitcher.class);
	
	private Boolean initStage = true;
	private LayerSwitchHelper helper;
	public UISelectMany selector;
	Hashtable<String, Service> services;
	Hashtable<String, String>  layerNames;
	private GeoJsfMapHelper geoJsfMap;
	
	public void decode(FacesContext context)
	{
		logger.info("Entering decode");
		
		java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");
		
	    logger.info("Got this event: " +behaviorEvent);
	    
	    if (null!= behaviorEvent && behaviorEvent.equals("layerSwitch"))
			{
				String serviceId = params.get("org.geojsf.switch.service");
				String layerId   = params.get("org.geojsf.switch.layer");
				Boolean active   = new Boolean(params.get("org.geojsf.switch.on"));
				logger.info("Trying to generate command to set layer " +layerId +" of service " +serviceId +" to " +active);				
				helper = new LayerSwitchHelper(services, layerNames);
				logger.debug("Current LayerSwitchHelper content: " +helper.toString());
		//		String toggleCommand = helper.toggleLayer(serviceId, layerId, active);
				String toggleCommand = helper.toggleLayer(layerId);
				services = helper.getServices();
				logger.info("Sending layer switch command to JavaScript client logic: " +toggleCommand +" to switch layer " +layerId +" of service " +serviceId +" to " +active);
				RequestContext.getCurrentInstance().addCallbackParam("toggleLayer", toggleCommand);
				RequestContext.getCurrentInstance().addCallbackParam("switchLayer", true);
			}
	    java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.isEmpty())
		{
			logger.error("no behaviors.exiting.");
			return;
		}
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		
		if (initStage)
		{
			
			String mapId = (String) this.getAttributes().get("mapId");
			Map map = (Map) this.getFacesContext().getViewRoot().findComponent(mapId);
			logger.debug("LayerSwitcher found Map component: " +map.getClientId() +" with " +map.getServiceList().size() +" services.");
					
			LayerSwitchHelper helper = new LayerSwitchHelper(map.getServiceList());
			services   = helper.getServices();
			layerNames = helper.getLayerNames();
			for (String serviceId : services.keySet())
			{
				org.geojsf.component.LayerSwitchHelper.Service service = services.get(serviceId);
				for (String layerId : service.getLayer().keySet())
				{
					String value = serviceId +":" +layerId;
					String label = "Show name for " +layerId;
					String id    = "layerselect" +layerId;
					SelectBooleanCheckbox checkbox = new SelectBooleanCheckbox();
					
					checkbox.setOnchange("GeoJSF.switchLayer('" +serviceId +"','" +layerId +"','" +this.getNamingContainer().getClientId() +":" +id +"_input')");
					checkbox.setValue(value);
					checkbox.setTitle(label);
					checkbox.setLabel(label);
					checkbox.setId(id);
					
					AjaxBehavior ajax = new AjaxBehavior();
					ajax.setOncomplete("GeoJSF.performLayerSwitch(xhr, status, args)");
					
					checkbox.addClientBehavior("layerSwitch", ajax);
				//  Inactive since a new approach utilizing the JavaScript API externally is being tested.	
				//	this.getChildren().add(checkbox);
				}
			}
			this.encodeChildren(ctx);
			initStage=false;
		}
	}
	
	// -------------------------------------------
	// JSF Methods for State Saving and Event Name
	// -------------------------------------------
	
	@Override
	public void restoreState(FacesContext context, Object state) {
	    Object[] storedState = (Object[]) state;
	    logger.info("Restoring state.");
	    initStage   = (Boolean) storedState[0];
	    services    = (Hashtable<String, Service>) storedState[1];
	    layerNames  = (Hashtable<String, String>) storedState[2];
		helper      = new LayerSwitchHelper(services, layerNames);
		logger.debug("Current LayerSwitchHelper content: " +helper.toString());
	}
	
	@Override
	public Object saveState(FacesContext context) {
	    Object[] rtrn = new Object[3];
	    rtrn[0] = initStage;
	    rtrn[1] = services;
	    rtrn[2] = layerNames;
	    return rtrn;
	}
			
	

	public GeoJsfMapHelper<DefaultGeoJsfLang, DefaultGeoJsfDescription, DefaultGeoJsfService, DefaultGeoJsfLayer, DefaultGeoJsfMap, DefaultGeoJsfView> getGeoJsfMap() {
		return geoJsfMap;
	}

	public void setGeoJsfMap(
			GeoJsfMapHelper<DefaultGeoJsfLang, DefaultGeoJsfDescription, DefaultGeoJsfService, DefaultGeoJsfLayer, DefaultGeoJsfMap, DefaultGeoJsfView> geoJsfMap) {
		this.geoJsfMap = geoJsfMap;
	}
	
	@Override
	public String getFamily() {return null;}


	public Boolean getInitStage() {return initStage;}
	public void setInitStage(Boolean initStage) {this.initStage = initStage;}

	public LayerSwitchHelper getHelper() {return helper;}
	public void setHelper(LayerSwitchHelper helper) {this.helper = helper;}

	public Hashtable<String, Service> getServices() {return services;}
	public void setServices(Hashtable<String, Service> services) {this.services = services;}
}
