package org.geojsf.util.wfs;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.geojsf.factory.xml.gml.XmlCoordinatesFactory;
import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;
import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.xml.geojsf.Coordinate;
import org.geojsf.xml.gml.Coordinates;
import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.wfs.GetFeature;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfsPointQuery<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(WfsPointQuery.class);
	
	private String url;
	private LAYER layer;
	private WfsGetFeaturePropertyProvider propertyProvider;
	private UtilsIdFacade fGeo;
	
	private Namespace nsGml;
		
	public WfsPointQuery(UtilsIdFacade fGeo, String url, WfsGetFeaturePropertyProvider propertyProvider, LAYER layer)
	{
		this.fGeo=fGeo;
		this.url=url;
		this.layer=layer;
		this.propertyProvider=propertyProvider;
		logger.info("Using URL:"+url+" with layer:"+layer);
		
		nsGml = Namespace.getNamespace("gml", "http://www.opengis.net/gml");
	}
	
	public <T extends EjbWithId> List<T> execute(Class<T> type, Coordinate coordinate, Distance distance)
	{
		return execute(type,XmlCoordinatesFactory.build(coordinate),distance);
	}
	
	public <T extends EjbWithId> List<T> execute(Class<T> type, Coordinates coordinates, Distance distance)
	{				
		GetFeature gf = PointQueryFactory.cGetFeature(propertyProvider.getWorkspace()+":"+layer.getCode(),propertyProvider.getProperties(type),coordinates,distance);
		
		WfsHttpRequest r = new WfsHttpRequest(url+"/wcs");
		
		Document doc = r.request(gf);
		
		Namespace nsQuery = propertyProvider.getNameSpace();
		StringBuffer xpath = new StringBuffer();
		xpath.append("//gml:featureMember");
		xpath.append("/").append(nsQuery.getPrefix()).append(":").append(layer.getCode());
//		xpath.append("/").append(nsQuery.getPrefix()).append(":").append("fid");
		
		logger.info("XPATH: "+xpath.toString());
		XPathExpression<Element> xpe = XPathFactory.instance().compile(xpath.toString(),Filters.element(), null,nsQuery,nsGml);
		List<Element> elements = xpe.evaluate(doc);
		logger.info("Elements: "+elements.size());
		
//		JDomUtil.debug(doc);
		
		List<T> result = new ArrayList<T>();
		for (Element e : elements)
		{	
			try
			{
				String s = e.getAttributeValue("fid");
				Long id = new Long(s.substring(s.lastIndexOf(".")+1));
				result.add(fGeo.find(type, id));
			}
			catch (UtilsNotFoundException ex)
			{
				logger.error(ex.getMessage());
			}
		}
		return result;
	}
}