package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;
import java.util.Objects;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.Service;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlServiceFactory <L extends JeeslLang, D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,?,?>,
							VIEW extends GeoJsfView<LAYER,?,VIEW>,
							VP extends GeoJsfViewPort>
					implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlServiceFactory.class);
	public static final long serialVersionUID=1;
	
	private Service q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlLayerFactory<L,D,CATEGORY,SERVICE,LAYER,VIEW,VP> xfLayer;
	
	public XmlServiceFactory(Query query) {this(query.getService());}
	public XmlServiceFactory(Service q)
	{
		this.q=q;
		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(q.isSetLayer()) {xfLayer = new XmlLayerFactory<>(q.getLayer().get(0));}
	}

	public Service build (SERVICE ejb)
	{
		Service xml = new Service();
		if(Objects.nonNull(q.getCode())) {xml.setCode(ejb.getCode());}
		if(q.isSetWms()){xml.setWms(ejb.getWms());}
		if(q.isSetWcs()){xml.setWcs(ejb.getWcs());}
		
		if(q.isSetLangs()) {xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()) {xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		
		if(q.isSetLayer() && ejb.getLayer()!=null)
		{
			for(LAYER layer : ejb.getLayer())
			{
				xml.getLayer().add(xfLayer.build(layer));
			}
		}

		return xml;
	}
	
	public static Service build(String code, String wms, String wcs)
	{
		Service xml = new Service();
		xml.setCode(code);
		xml.setWms(wms);
		xml.setWcs(wcs);
		return xml;
	}
}