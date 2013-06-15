package org.geojsf.factory.txt;

import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtOpenlayersLayerFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtOpenlayersLayerFactory.class);
	
	public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>> 
		String buildLayerString(SERVICE service)
	{
		List<LAYER> layers = service.getLayer();
		StringBuffer sb = new StringBuffer();
		for (LAYER layer : layers)
		{
			sb.append(layer.getCode() +",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
}