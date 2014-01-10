package org.geojsf.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectMany;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.controller.util.GeoJsfMapHelper;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "geojsf", name = "geojsf.css", target = "head")})
@FacesComponent(value="org.geojsf.component.LayerSwitcher")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class LayerSwitcher extends UIComponentBase implements ClientBehaviorHolder{

	final static Logger logger = LoggerFactory.getLogger(LayerSwitcher.class);
	public UISelectMany selector;
	private GeoJsfMapHelper geoJsfMap;
	
	private java.util.Map<String, String> availableLayers;
	private String[] selectedArray;
	

	private String layerArray;
	
	Gson gson = new Gson();
	
	public String init()
	{			
		geoJsfMap = (GeoJsfMapHelper) getAttributes().get("value");
		availableLayers = new HashMap<String, String>();
		
		logger.info("Loading Layer options.");
		for (Object o : geoJsfMap.getDmLayer())
		{
			GeoJsfLayer layer = (GeoJsfLayer) o;
			availableLayers.put(layer.getCode(), layer.getCode());
		}
		selectedArray = new String[availableLayers.size()];
		return layerArray;
	}
	
	public void decode(FacesContext context)
	{
		logger.info("Entering decode");
		java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");
	    logger.info("Got this event: " +behaviorEvent);
	    SelectManyCheckbox selectorComponent = (SelectManyCheckbox) this.getFacesContext().getViewRoot().findComponent("fLayer:selector");
	    String[] values = (String[]) selectorComponent.getValue();
	    StringBuffer buffer = new StringBuffer();
	    for (String value : values)
	    {
	    	buffer.append(value + "---");
	    }
	    RequestContext.getCurrentInstance().addCallbackParam("test", gson.toJson(selectorComponent.getValue()));
		
	    java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.isEmpty())
		{
			logger.error("no behaviors.exiting.");
			return;
		}
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		init();
		
		//Add the SelectManyCheckbox
		Application app = FacesContext.getCurrentInstance().getApplication();
		selector = (HtmlSelectManyCheckbox) app.createComponent(HtmlSelectManyCheckbox.COMPONENT_TYPE);
		selector = new SelectManyCheckbox();
		selector.setId("selector");
		selector.setValue(selectedArray);
		selector.setTransient(false);
		Integer number = 0;
		for (Object o : geoJsfMap.getDmLayer())
		{
			GeoJsfLayer layer = (GeoJsfLayer) o;
			selectedArray[number]=layer.getService() +":" +layer.getCode();
			UISelectItem item = new UISelectItem();
			item.setItemLabel(layer.getService().getCode() +"->" +layer.getCode());
			item.setItemValue(layer.getService() +":" +layer.getCode());
			item.setId("layerSelect" +number);
			selector.getChildren().add(item);
			number++;
		}
		this.getChildren().add(selector);
		logger.info("Populated these values: " +selector.getSelectedValues().toString());
		
		
		//Add a AJAX client behavior to enable a client and server communication
		AjaxBehavior ajax = new AjaxBehavior();
		ajax.setProcess("@form");
		ajax.setOncomplete("GeoJSF.testAjaxData(xhr, status, args)");
		selector.addClientBehavior("change", ajax);
//		ajax.setOncomplete("GeoJSF.setLayers('" +this.getParent().getClientId() +":selector:', "+number+")");
	}
			
	

	public GeoJsfMapHelper<DefaultGeoJsfLang, DefaultGeoJsfDescription, DefaultGeoJsfService, DefaultGeoJsfLayer, DefaultGeoJsfView, DefaultGeoJsfViewLayer> getGeoJsfMap() {
		return geoJsfMap;
	}

	public void setGeoJsfMap(
			GeoJsfMapHelper<DefaultGeoJsfLang, DefaultGeoJsfDescription, DefaultGeoJsfService, DefaultGeoJsfLayer, DefaultGeoJsfView, DefaultGeoJsfViewLayer> geoJsfMap) {
		this.geoJsfMap = geoJsfMap;
	}
	
	public java.util.Map<String, String> getAvailableLayers() {
		return availableLayers;
	}

	public void setAvailableLayers(java.util.Map<String, String> availableLayers) {
		this.availableLayers = availableLayers;
	}

	@Override
	public String getFamily() {
		return null;
	}
	public String[] getSelectedArray() {
		return selectedArray;
	}

	public void setSelectedArray(String[] selectedArray) {
		this.selectedArray = selectedArray;
	}
}
