package org.geojsf.factory.txt;

import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtOpenlayersLayerFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtOpenlayersLayerFactory.class);
	
	public static <SERVICE extends GeoJsfService<?,?,LAYER>,
					LAYER extends GeoJsfLayer<?,?,?,SERVICE,?,?,?>> 
		String buildLayerString(SERVICE service)
	{
		List<LAYER> layers = service.getLayer();
		StringBuffer sb = new StringBuffer();
//		for (LAYER layer : layers)
		for(int i=layers.size()-1;i>=0;i--) //GEO-64	
		{
			LAYER layer = layers.get(i);
			sb.append(layer.getCode() +",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
}