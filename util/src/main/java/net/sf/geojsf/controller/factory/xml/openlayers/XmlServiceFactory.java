package net.sf.geojsf.controller.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;

import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.openlayers.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlServiceFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlServiceFactory.class);
	public static final long serialVersionUID=1;
	
	private Service q;
	
	public XmlServiceFactory(Query query) {this(query.getService());}
	public XmlServiceFactory(Service q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		Service build (GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL> ejb)
	{
		Service xml = new Service();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetUrl()){xml.setUrl(ejb.getUrl());}
		
		if(q.isSetLayer() && ejb.getLayer()!=null && ejb.getLayer().size()>0)
		{
			XmlLayerFactory f = new XmlLayerFactory(q.getLayer().get(0));
			for(GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL> layer : ejb.getLayer())
			{
				xml.getLayer().add(f.build(layer));
			}
		}
		
		return xml;
	}
}
