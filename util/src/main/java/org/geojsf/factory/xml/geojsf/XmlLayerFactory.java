package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.geojsf.factory.xml.geojsf.meta.XmlViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Query;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLayerFactory <L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,?,?>,
								VIEW extends GeoJsfView<LAYER,?,VIEW>,
								VP extends GeoJsfViewPort>
							implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Layer q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlCategoryFactory<L,D,CATEGORY> xfCatgory;
	private XmlViewPortFactory<VP> xfViewport;
	private XmlServiceFactory<L,D,CATEGORY,SERVICE,LAYER,VIEW,VP> xfService;
	
	public XmlLayerFactory(Query query){this(query.getLayer());}
	public XmlLayerFactory(Layer q)
	{
		this.q=q;
		
		if(Objects.nonNull(q.getLangs())) {xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(Objects.nonNull(q.getDescriptions())) {xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(Objects.nonNull(q.getCategory())) {xfCatgory = new XmlCategoryFactory<>(q.getCategory());}
		if(Objects.nonNull(q.getViewPort())) {xfViewport = new XmlViewPortFactory<>(q.getViewPort());}
		if(Objects.nonNull(q.getService()))  {xfService = new XmlServiceFactory<>(q.getService());}
	}

	public Layer build (LAYER ejb)
	{
		Layer xml = new Layer();
		
		if(Objects.nonNull(q.getCode())) {xml.setCode(ejb.getCode());}
		if(Objects.nonNull(q.isTemporal())) {xml.setTemporal(ejb.isTemporalLayer());}
		if(Objects.nonNull(q.isSql()))
		{
			if(ejb.isSqlLayer()!=null){xml.setSql(ejb.isSqlLayer());}
			else{xml.setSql(false);}
		}
		
		if(Objects.nonNull(q.getLangs())) {xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(Objects.nonNull(q.getDescriptions())) {xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		
		if(Objects.nonNull(q.getService())) {xml.setService(xfService.build(ejb.getService()));}
		if(ObjectUtils.allNotNull(q.getCategory(),ejb.getCategory())) {xml.setCategory(xfCatgory.build(ejb.getCategory()));}
		if(ObjectUtils.allNotNull(q.getViewPort(),ejb.getViewPort())) {xml.setViewPort(xfViewport.build(ejb.getViewPort()));}
		
		return xml;
	}
	
	public Layer build (VIEW ejb)
	{
		Layer xml = new Layer();
		
		if(Objects.nonNull(q.getCode())) {xml.setCode(ejb.getLayer().getCode());}
				
		return xml;
	}
	
	public static Layer build(String code)
	{
		Layer xml = new Layer();
		xml.setCode(code);
		return xml;
	}
}