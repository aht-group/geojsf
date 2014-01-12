package org.geojsf.util;

import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeojsfDatastructureDebugger
{
	final static Logger logger = LoggerFactory.getLogger(GeojsfDatastructureDebugger.class);
	
	public static void debug(DefaultGeoJsfMap map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Map.").append(map.getId());
		sb.append(" code=").append(map.getCode());
		logger.info(sb.toString());
		for(DefaultGeoJsfView view : map.getViews())
		{
			debug(1,view);
		}
	}
	
	public static void debug(DefaultGeoJsfView view){debug(0,view);}
	public static void debug(int indent, DefaultGeoJsfView view)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("View.").append(view.getId());
		sb.append(" visible=").append(view.isVisible());
		logger.info(sb.toString());
		debug(indent+1,view.getLayer());
	}
	
	public static void debug(DefaultGeoJsfLayer layer){debug(0,layer);}
	public static void debug(int indent, DefaultGeoJsfLayer layer)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Layer.").append(layer.getId());
		sb.append(" code=").append(layer.getCode());
		logger.info(sb.toString());
		debug(indent+1,layer.getService());
	}
	
	public static void debug(DefaultGeoJsfService service){debug(0,service);}
	public static void debug(int indent, DefaultGeoJsfService service)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Service.").append(service.getId());
		sb.append(" code=").append(service.getCode());
		logger.info(sb.toString());
		for(DefaultGeoJsfLayer layer : service.getLayer())
		{
			debug(indent+1,layer);
		}
	}
	
	private static String tab(int size)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<size;i++)
		{
			sb.append("\t");
		}
		return sb.toString();
	}
}