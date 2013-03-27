package org.geojsf.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.xml.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Map")
public class Map  extends UINamingContainer implements ClientBehaviorHolder{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private ArrayList<DefaultGeoJsfService> serviceList;
	
	private Coordinates coords = new Coordinates();
	
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

	@Override
	public void addClientBehavior(String eventName, ClientBehavior behavior) {
		logger.debug("Adding client behavior for " +eventName);
		logger.debug("Publishing new MapClickEvent...");
	}

	@Override
	public Collection<String> getEventNames() {
		ArrayList<String> events = new ArrayList<String>();
		events.add("mapClick");
		return events;
	}

	@Override
	public java.util.Map<String, List<ClientBehavior>> getClientBehaviors() {
		return new Hashtable<String, List<ClientBehavior>>();
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
}
