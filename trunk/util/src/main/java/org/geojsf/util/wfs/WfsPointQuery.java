package org.geojsf.util.wfs;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.xml.JDomUtil;

import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;
import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.xml.gml.Coordinates;
import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.wfs.GetFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfsPointQuery<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(WfsPointQuery.class);
	
	private String url;
	private String layerCode;
	private WfsGetFeaturePropertyProvider propertyProvider;
		
	public WfsPointQuery(String url, WfsGetFeaturePropertyProvider propertyProvider, LAYER layer)
	{
		this.url = url;
		this.propertyProvider=propertyProvider;
		layerCode = "lis:"+layer.getCode();
		logger.info("Using URL:"+url+" with layer:"+layer);
	}
	
	public <T extends EjbWithId> void execute(Class<T> type, Coordinates coordinates, Distance distance)
	{						
		GetFeature gf = PointQueryFactory.cGetFeature(layerCode,propertyProvider.getProperties(type),coordinates,distance);
		
		WfsHttpRequest r = new WfsHttpRequest(url+"/wcs");
		JDomUtil.debug(r.request(gf));
	}
}