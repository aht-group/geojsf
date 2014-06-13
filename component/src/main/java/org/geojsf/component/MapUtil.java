package org.geojsf.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.exception.UnconsistentConfgurationException;
import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.txt.TxtIsoTimeFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtil<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
{	
	final static Logger logger = LoggerFactory.getLogger(MapUtil.class);
	
	private Class<SERVICE> clService;
	private Class<LAYER> clLayer;
	private Class<MAP> clMap;
	private Class<VIEW> clView; 
	
	@SuppressWarnings("unchecked")
	public MapUtil(FacesContext context)
	{
		if(context.getExternalContext().getInitParameter("org.geojsf.SERVICE")==null)
		{
			//GEO-131
//			clService = (Class<SERVICE>)DefaultGeoJsfService.class;
//			clLayer = (Class<LAYER>)DefaultGeoJsfLayer.class;
//			clMap = (Class<MAP>)DefaultGeoJsfMap.class;
//			clView = (Class<VIEW>)DefaultGeoJsfView.class;
		}
		else
		{
			try
			{
				clService = (Class<SERVICE>) Class.forName(context.getExternalContext().getInitParameter("org.geojsf.SERVICE"));
				clLayer = (Class<LAYER>) Class.forName(context.getExternalContext().getInitParameter("org.geojsf.LAYER"));
				clMap = (Class<MAP>) Class.forName(context.getExternalContext().getInitParameter("org.geojsf.MAP"));
				clView = (Class<VIEW>) Class.forName(context.getExternalContext().getInitParameter("org.geojsf.VIEW"));
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		logger.info("Using SERVICE "+clService.getName());
		logger.info("Using LAYER "+clLayer.getName());
		logger.info("Using MAP "+clMap.getName());
		logger.info("Using MAP "+clView.getName());
	}
	
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
	
	public static void updateTime(UIComponent map, Long time)
	{
		for (UIComponent comp : map.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("Time"))
			{
				Time t = (Time) comp;
				t.setValue(new Date(time));
				logger.info("updated time to " +t.getValue().toGMTString());
			}
		}
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
	
	public MAP initLayerConfiguration(Map<L,D,SERVICE,LAYER,MAP,VIEW> map) throws Exception
	{
		 map.setTemporalLayerNames(new ArrayList<String>());
		 map.setServiceList(new ArrayList<SERVICE>());
		 
		 
		 MAP dmMap=null;
		try {
			dmMap = clMap.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 dmMap.setViews(new ArrayList<VIEW>());
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
				 logger.debug("No value given - falling back to simple version");
				 
				 Hashtable<Integer, SERVICE> services = new Hashtable<Integer, SERVICE>();
				 Integer layerId = 1;

				 MapUtil.setBaseLayer(map);
				 
				 for (UIComponent child : map.getChildren())
				 {
					 logger.info("Found child of type: " +child.getClass().getSimpleName());
					 if (child.getClass().getSimpleName().equals("Layer"))
					 {
						 Layer layer = (Layer) child;
						 Integer serviceId = 0;
						 String  layerUrl  = layer.getUrl();
						 String  layerList = layer.getLayers();
						 
						 SERVICE dmService = clService.newInstance();
						 VIEW dmView       = clView.newInstance();
						 
						 // Check if there is already a service prepared for this URL and
						 // assign the correct (or a new incremented) ID/Service for that
						 for (Integer id : services.keySet())
						 {
							 if (services.get(id).getUrl().equals(layerUrl))
							 {
								 serviceId = id;
								 dmService = (SERVICE) services.get(id);
							 }
						 }
						 if (serviceId.equals(0))
						 {
							 serviceId = services.size()+1;
							 dmService  = clService.newInstance();
							 dmService.setUrl(layerUrl);
							 dmService.setId(serviceId);
							 dmService.setLayer(new ArrayList<LAYER>());
							 services.put(serviceId, dmService);
						 }
						 
						 // Now add all the Layers to View and Service
						 String layerString = (String)layerList;
						 for (String string : layerString.split(","))
						 {
							 LAYER layerToAdd = clLayer.newInstance();
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
			 MAP mapDm = (MAP)map.getAttributes().get("value");
			 dmMap           = (MAP)map.getAttributes().get("value");
			 GeoJsfServiceFactory<L,D,SERVICE,LAYER,MAP,VIEW> fService;
			 fService = GeoJsfServiceFactory.factory(clService);
			 
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
		sb.append("height: ");
		if(height!=null){sb.append(height +"px;");}
		else{sb.append("100%;");}
		return sb.toString();
	}
	
	

}
