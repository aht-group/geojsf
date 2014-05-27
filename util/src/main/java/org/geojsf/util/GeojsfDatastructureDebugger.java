package org.geojsf.util;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeojsfDatastructureDebugger <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
{
	final static Logger logger = LoggerFactory.getLogger(GeojsfDatastructureDebugger.class);
	
	public void debug(MAP map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Map.").append(map.getId());
		sb.append(" code=").append(map.getCode());
		logger.info(sb.toString());
		for(VIEW view : map.getViews())
		{
			debug(1,view);
		}
	}
	
	public void debug(VIEW view){debug(0,view);}
	public void debug(int indent, VIEW view)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("View.").append(view.getId());
		sb.append(" visible=").append(view.isVisible());
		logger.info(sb.toString());
		debug(indent+1,view.getLayer());
	}
	
	public void debug(LAYER layer){debug(0,layer);}
	public void debug(int indent, LAYER layer)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Layer.").append(layer.getId());
		sb.append(" code=").append(layer.getCode());
		logger.info(sb.toString());
		debug(indent+1,layer.getService());
	}
	
	public void debug(SERVICE service){debug(0,service);}
	public void debug(int indent, SERVICE service)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Service.").append(service.getId());
		sb.append(" code=").append(service.getCode());
		logger.info(sb.toString());
		for(LAYER layer : service.getLayer())
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