package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.Service;
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

	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		Service build (GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> ejb)
	{
		Service xml = new Service();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetWms()){xml.setWms(ejb.getWms());}
		if(q.isSetWcs()){xml.setWcs(ejb.getWcs());}
		
		if(q.isSetLayer() && ejb.getLayer()!=null && ejb.getLayer().size()>0)
		{
			XmlLayerFactory f = new XmlLayerFactory(q.getLayer().get(0));
			for(GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> layer : ejb.getLayer())
			{
				xml.getLayer().add(f.build(layer));
			}
		}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		return xml;
	}
}
