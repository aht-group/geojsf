package org.geojsf.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.txt.TxtIsoTimeFactory;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtil<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
{	
	final static Logger logger = LoggerFactory.getLogger(MapUtil.class);
	
	private Class<SERVICE> clService;
	private Class<LAYER>   clLayer;
	private Class<MAP>     clMap;
	private Class<VIEW>    clView; 
	
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
	
	public MAP buildViewsFromLayers(UIComponent map)
	{
		MAP dmMap = null;
		try { dmMap = clMap.newInstance();} 
		catch (InstantiationException e) {
			e.printStackTrace();}
		catch (IllegalAccessException e) {
			e.printStackTrace();}
		
		dmMap.setViews(new ArrayList<VIEW>());
		logger.debug("No value given - falling back to simple version");
				 
		Long viewId     = new Long(0);
		Long layerId    = new Long(0);
		Long serviceId  = new Long(0);
		
		Hashtable<String, SERVICE> serviceForUrl = new Hashtable<String, SERVICE>();
		for (UIComponent child : map.getChildren())
		{
			if (child.getClass().getSimpleName().equals("Layer"))
			{
				// Get the Layer component
				Layer compLayer = (Layer) child;
				
				// Create a new VIEW, LAYER and SERVICE for this
				VIEW view       = null;
				LAYER dmLayer   = null;
				SERVICE service = null;
				
				try {
					view    = clView.newInstance();
					dmLayer = clLayer.newInstance();
					service = clService.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				
				// Assign data from component and generic IDs
				view.setId(viewId);
				viewId++;
				view.setVisible(true);
				dmLayer.setId(layerId);
				layerId++;
				dmLayer.setCode(compLayer.getLayers());
				
				if (!serviceForUrl.containsKey(compLayer.getUrl()))
				{
					service.setUrl(compLayer.getUrl());
					service.setId(serviceId);
					serviceId++;
					dmLayer.setService(service);
					serviceForUrl.put(compLayer.getUrl(), service);
				}
				else
				{
					service = serviceForUrl.get(compLayer.getUrl());
					dmLayer.setService(service);
				}
				view.setLayer(dmLayer);
				dmMap.getViews().add(view);
			}
		}
		return dmMap;
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
	
	
	/*	
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
	*/
	
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
