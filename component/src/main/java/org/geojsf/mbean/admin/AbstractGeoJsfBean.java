package org.geojsf.mbean.admin;

import java.io.Serializable;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.core.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.factory.ejb.meta.EjbGeoScaleFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.factory.factory.GeoJsfFactoryFactory;
import org.geojsf.factory.factory.GeoSldFactoryFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
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
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractGeoJsfBean <L extends UtilsLang, D extends UtilsDescription,
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
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfBean.class);
	
	protected String[] langKeys;
	protected GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	protected final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore;
	protected final GeoMetaFactoryBuilder<L,D,DS,VP> fbMeta;
	protected final GeoSldFactoryBuilder<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld;
	
	protected final GeoSldFactoryFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> ffSld;
	protected final GeoJsfFactoryFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> ffGeo;
	
	protected final EjbLangFactory<L> efLang;
	protected final EjbDescriptionFactory<D> efDescription;
	
	protected final EjbGeoCategoryFactory<L,D,CATEGORY> efCategory;
	protected final EjbGeoServiceFactory<L,D,SERVICE> efService;
	protected final EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER> efLayer;
	protected final EjbGeoMapFactory<L,D,MAP> efMap;
	protected final EjbGeoScaleFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efScale;
	protected final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	protected final EjbGeoViewPortFactory<VP> efViewPort;
	
	protected final EjbGeoDataSourceFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efDs;
	protected final EjbGeoSldTemplateFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efTemplate;
	protected final EjbGeoSldFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efSld;
	
	public AbstractGeoJsfBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
							GeoMetaFactoryBuilder<L,D,DS,VP> fbMeta,
							GeoSldFactoryBuilder<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		this.fbCore=fbCore;
		this.fbMeta=fbMeta;
		this.fbSld=fbSld;

		
		ffSld = GeoSldFactoryFactory.factory(fbSld.getClassSld());
		ffGeo = GeoJsfFactoryFactory.factory(fbCore.getClassL(),fbCore.getClassD(),fbCore.getClassScale(),fbMeta.getClassDs());
		
		efLang = EjbLangFactory.factory(fbCore.getClassL());
	    efDescription = EjbDescriptionFactory.factory(fbCore.getClassD());
	    	
	    efCategory = fbCore.ejbCategory();
	    efService = fbCore.ejbService();
	    efLayer = fbCore.ejbLayer();
	    efMap = fbCore.ejbMap();
	    efScale = ffGeo.ejbScale();
	    efView = fbCore.ejbView();
	    efViewPort = fbMeta.ejbViewPort();
    	
		efDs = ffGeo.ejbDs();
		efSld = ffSld.sld();
		efTemplate = EjbGeoSldTemplateFactory.factory(fbSld.getClassTemplate());
	}
	
	protected void postConstructGeojsf(String[] langKeys, GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		postInit();
	}
	
	protected void postInit(){}
}