package org.geojsf.component;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.faces.component.UIComponent;

import org.geojsf.exception.UnconsistentConfgurationException;
import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.txt.TxtIsoTimeFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtil
{	
	final static Logger logger = LoggerFactory.getLogger(MapUtil.class);
	
	public static String searchTimeDefinition(UIComponent map)
	{
		String timeInfo = "";
		for (UIComponent comp : map.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("Time"))
			{
				Time t = (Time) comp;
				if (null!=t.getValue())
				{
					timeInfo = TxtIsoTimeFactory.toDate(t.getValue());
				}
				else if (null!=t.getRangeFrom())
				{
					timeInfo = TxtIsoTimeFactory.toRange(t.getRangeFrom(), t.getRangeTo());
				}
				else
				{
					timeInfo = TxtIsoTimeFactory.toList(t.getTimeList());
				}
				logger.info("time set to " +timeInfo);
			}
		}
		return timeInfo;
	}
	
	public static Boolean containsLayer(UIComponent map)
	{
		Boolean layer = false;
		for (UIComponent child : map.getChildren())
		 {
			 if (child instanceof Layer)
			 {
				 layer = true;
			 }
		 }
		return layer;
	}
	
	public static void searchScale(UIComponent component)
	{
		for (UIComponent comp : component.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("Scales"))
			{
				logger.info("found scale!");
			}
		}
	}
	
	@Deprecated
	public static DefaultGeoJsfService layerToService(Layer layer)
	{
		 String url = (String) layer.getUrl();
		 DefaultGeoJsfService service = new DefaultGeoJsfService();
		 service.setUrl(url);
		 service.setCode(layer.getName());
		 Boolean temporal = layer.getHasTemporalLayer();
		 Object layerList = layer.getLayers();
		 logger.debug("Detecting layer definition type..." +layerList.getClass().getSimpleName());
		 if (layerList.getClass().getSimpleName().equals("String"))
		 {
			 logger.debug("Detected layer list given as Strings");
			 String layerString = (String)layerList;
			 for (String string : layerString.split(","))
			 {
				 DefaultGeoJsfLayer layerToAdd = new DefaultGeoJsfLayer();
				 layerToAdd.setTemporalLayer(temporal);
				 layerToAdd.setCode(string);
				 service.getLayer().add(layerToAdd);
			 }
		 }
	//	 ArrayList<DefaultGeoJsfService> singleValue = new ArrayList<DefaultGeoJsfService>();
	//	 singleValue.add(service);
	//	 getAttributes().put("value", singleValue);
	//	 serviceList.add(service);
		 return service;
	}
	
	public static ArrayList<String> hasTemporalLayer(GeoJsfService service)
	{
		ArrayList<String> temporalLayerNames = new ArrayList<String>();
		for (Object o : service.getLayer())
		{
			GeoJsfLayer layer = (GeoJsfLayer) o;
			if (layer.isTemporalLayer()) {temporalLayerNames.add(service.getCode());}
		}
		return temporalLayerNames;
	}
	
	public static void setBaseLayer(UIComponent component)
	{
		Integer lastLayerIndex = 0;
		ArrayList<UIComponent> children = (ArrayList<UIComponent>) component.getChildren();
		for (int i=0; i<children.size(); i++)
	//	for (UIComponent child : this.getChildren())
		{
			UIComponent child = children.get(i);
			 if (child.getClass().getSimpleName().equals("Layer"))
			 {
				 lastLayerIndex = i;
				 Layer layer = (Layer) child;
				 if (null==layer.getName())
				 {
					 layer.setName("Layer" +i);
				 }
			 }
		 }
		Layer baseLayer = (Layer) component.getChildren().get(lastLayerIndex);
		baseLayer.setIsBaseLayer(true);
	}
	
	public static DefaultGeoJsfMap initLayerConfiguration(Map map) throws Exception
	{
		 map.setTemporalLayerNames(new ArrayList<String>());
		 map.setServiceList(new ArrayList<DefaultGeoJsfService>());
		 DefaultGeoJsfMap dmMap         = new DefaultGeoJsfMap();
		 dmMap.setViews(new ArrayList<DefaultGeoJsfView>());
		 logger.info("Initial layer configuration.");
		 logger.debug("Checking value existence ...");
		 if (map.getAttributes().get("value")==null)
		 {
			 if (!MapUtil.containsLayer(map))
			 {
				 throw new Exception("No value or layers given!");
			 }
			 else
			 {
				 Hashtable<Integer, DefaultGeoJsfService>          services         = new Hashtable<Integer, DefaultGeoJsfService>();
				 Hashtable<Integer, ArrayList<DefaultGeoJsfLayer>> layersForService = new Hashtable<Integer, ArrayList<DefaultGeoJsfLayer>>();
				 Integer layerId = 1;
				 
				 MapUtil.setBaseLayer(map);
				 logger.debug("No value given - falling back to simple version");
				 for (UIComponent child : map.getChildren())
				 {
					 logger.info("Found child of type: " +child.getClass().getSimpleName());
					 if (child.getClass().getSimpleName().equals("Layer"))
					 {
						 Layer layer = (Layer) child;
						 Integer serviceId = 0;
						 String  layerUrl  = layer.getUrl();
						 String  layerList = layer.getLayers();
						 
						 DefaultGeoJsfService dmService = new DefaultGeoJsfService();
						 DefaultGeoJsfView dmView       = new DefaultGeoJsfView();
						 
						 // Check if there is already a service prepared for this URL and
						 // assign the correct (or a new incremented) ID/Service for that
						 for (Integer id : services.keySet())
						 {
							 if (services.get(id).getUrl().equals(layerUrl))
							 {
								 serviceId = id;
								 dmService = services.get(id);
							 }
						 }
						 if (serviceId.equals(0))
						 {
							 serviceId = services.size()+1;
							 dmService  = new DefaultGeoJsfService();
							 dmService.setUrl(layerUrl);
							 dmService.setId(serviceId);
							 dmService.setLayer(new ArrayList<DefaultGeoJsfLayer>());
							 services.put(serviceId, dmService);
						 }
						 
						 // Now add all the Layers to View and Service
						 String layerString = (String)layerList;
						 for (String string : layerString.split(","))
						 {
							 DefaultGeoJsfLayer layerToAdd = new DefaultGeoJsfLayer();
							 layerToAdd.setId(layerId++);
							 layerToAdd.setTemporalLayer(layer.getHasTemporalLayer());
							 layerToAdd.setCode(string);
							 layerToAdd.setService(dmService);
							 dmService.getLayer().add(layerToAdd);
							 dmView.setLayer(layerToAdd);
							 dmView.setVisible(true);
							 dmMap.getViews().add(dmView);
						 }
						 // Deprecated since migrating to new DataModel
						 // map.getServiceList().add(MapUtil.layerToService(layer));
						 map.getServiceList().add(dmService);
					 }
				 } 
			 }
		 }
		 else
		 {
			 @SuppressWarnings("rawtypes")
			 GeoJsfMap mapDm = (GeoJsfMap)map.getAttributes().get("value");
			 dmMap           = (DefaultGeoJsfMap)map.getAttributes().get("value");
			 GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fService;
			 fService = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
			 
			 map.setServiceList(fService.buildI(mapDm));
			 if (MapUtil.containsLayer(map))
			 {
				 throw new UnconsistentConfgurationException("layer tag found while value is given. Don't mix configurations!");
			 }
		 }
		 map.setTimeInfo(MapUtil.searchTimeDefinition(map));
		 return dmMap;
	}
	
	public static String buildStyle(Integer height, Integer width)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("width: ");
		if(width!=null){sb.append(width +"px;");}
		else{sb.append("100%;");}
		sb.append("height: " +height + "px;");
		return sb.toString();
	}
	
	

}
