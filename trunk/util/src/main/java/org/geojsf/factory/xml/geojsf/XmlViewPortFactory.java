package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.xml.geojsf.Scale;
import org.geojsf.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewPortFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewPortFactory.class);
	
	public static final long serialVersionUID=1;

	public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
		ViewPort build (MAP map)
	{
		ViewPort xml = new ViewPort();
		
		xml.setLon(map.getLon());
		xml.setLat(map.getLat());
		
		xml.setScale(new Scale());
		xml.getScale().setZoom(map.getZoom());
			
		return xml;
	}
}
