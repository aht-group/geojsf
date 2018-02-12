package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.factory.xml.geojsf.meta.XmlViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Query;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlLayerFactory <L extends UtilsLang,D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,?>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Layer q;
	
	public XmlLayerFactory(Query query){this(query.getLayer());}
	public XmlLayerFactory(Layer q)
	{
		this.q=q;
	}

	public 
		Layer build (GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD> ejb)
	{
		Layer xml = new Layer();
		
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetTemporal()){xml.setTemporal(ejb.isTemporalLayer());}
		if(q.isSetSql())
		{
			if(ejb.isSqlLayer()!=null){xml.setSql(ejb.isSqlLayer());}
			else{xml.setSql(false);}
		}	
		
		if(q.isSetService())
		{
			XmlServiceFactory f = new XmlServiceFactory(q.getService());
			xml.setService(f.build(ejb.getService()));
		}
		
		if(q.isSetCategory() && ejb.getCategory()!=null)
		{
			XmlCategoryFactory f = new XmlCategoryFactory(q.getCategory());
			xml.setCategory(f.build(ejb.getCategory()));
		}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		if(q.isSetViewPort() && ejb.getViewPort()!=null)
		{
			XmlViewPortFactory f = new XmlViewPortFactory(q.getViewPort());
			xml.setViewPort(f.build(ejb.getViewPort()));
		}
				
		return xml;
	}
	
	public <L extends UtilsLang,
			G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
			F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
			CATEGORY extends GeoJsfCategory<L,D,LAYER>,
			SERVICE extends GeoJsfService<L,D,LAYER>,
			LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
			MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
			SCALE extends GeoJsfScale<L,D>, 
			VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
			VP extends GeoJsfViewPort,
			DS extends GeoJsfDataSource<L,D,LAYER>,
			SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
			SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
			SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
			RULE extends GeoJsfSldRule<L,D,G>>
		Layer build (GeoJsfView<LAYER,MAP,VIEW> ejb)
	{
		Layer xml = new Layer();
		
		if(q.isSetCode()){xml.setCode(ejb.getLayer().getCode());}
				
		return xml;
	}
	
	public static Layer build(String code)
	{
		Layer xml = new Layer();
		xml.setCode(code);
		return xml;
	}
}
