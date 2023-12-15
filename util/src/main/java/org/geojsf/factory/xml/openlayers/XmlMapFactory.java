package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;
import java.util.Objects;

import org.geojsf.factory.xml.geojsf.meta.XmlViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Query;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapFactory <L extends JeeslLang, D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,?,?,VP,?,?>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort
							>
			implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Map q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlViewPortFactory xfViewport;
	
	public XmlMapFactory(Query query){this(query.getMap());}
	public XmlMapFactory(Map q)
	{
		this.q=q;
		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(q.isSetViewPort()) {xfViewport = new XmlViewPortFactory(q.getViewPort());}
	}

	public Map build (MAP ejb)
	{
		Map xml = new Map();
		if(Objects.nonNull(q.getCode())) {xml.setCode(ejb.getCode());}
				
		if(ejb.getViews()!=null && ejb.getViews().size()>0)
		{
			
		}
		
		if(q.isSetLangs()) {xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()) {xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		
		if(q.isSetViewPort() && ejb.getViewPort()!=null) {xml.setViewPort(xfViewport.build(ejb.getViewPort()));}
		
		return xml;
	}
}