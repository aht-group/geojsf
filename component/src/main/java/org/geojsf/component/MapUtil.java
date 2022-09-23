package org.geojsf.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.geojsf.factory.txt.TxtIsoTimeFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Scales;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtil<L extends JeeslLang,D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,?>>
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
	
	public static Hashtable<String,String> searchSqlViewParameters(UIComponent map)
	{
		Hashtable<String,String> parameters = new Hashtable<String,String>();
		for (UIComponent comp : map.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("SqlViewParameter"))
			{
				SqlViewParameter s = (SqlViewParameter) comp;
				if (null!=s.getValue())
				{
					parameters.put(s.getKey(), s.getValue());
				}
				if(logger.isTraceEnabled()) {logger.trace("Added SQLViewParameter: " +s.getKey() +"=" +s.getValue());}
			}
		}
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
		return parameters;
	}
	
	public static String renderSqlViewParameters(UIComponent map)
	{
		Hashtable<String,String> parameters = new Hashtable<String,String>();
		for (UIComponent comp : map.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("SqlViewParameter"))
			{
				SqlViewParameter s = (SqlViewParameter) comp;
				if (null!=s.getValue())
				{
					parameters.put(s.getKey(), s.getValue());
				}
				if(logger.isTraceEnabled()) {logger.trace("Added SQLViewParameter: " +s.getKey() +"=" +s.getValue());}
			}
		}
		StringBuffer sb = new StringBuffer();
		if (parameters.size()>0)
		{
			sb.append("params.viewparams  = '");
			for (String key : parameters.keySet())
			{
				sb.append(key +":" +parameters.get(key) +";");
			}
			sb.append("';");
		//	renderer.renderTextWithLB(sb.toString());
		}
		return sb.toString();
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
					service.setWms(compLayer.getUrl());
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
	
	public static Scales searchScale(UIComponent component)
	{
		for (UIComponent comp : component.getChildren())
		{
			if (comp.getClass().getSimpleName().toString().equals("ScalesContainer"))
			{
				logger.info("found scale!");
				ScalesContainer scales = (ScalesContainer) comp;
				return scales.getValue();
			}
		}
		return null;
	}
	
	public static Boolean containsLayer(UIComponent component)
	{
	    Boolean layer = false;
	    for (UIComponent child : component.getChildren())
	     {
		if (child instanceof Layer)
		{
		    layer = true;
		}
	     }
	    return layer;
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
	
	
	
	

}
