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
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfBean <L extends JeeslLang, D extends JeeslDescription,LOC extends JeeslLocale<L,D,LOC,?>,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicComponent<L,D,G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									SCALE extends GeoJsfScale<L,D>,
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									DS extends GeoJsfDataSource<L,D,LAYER>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<JL,L,D,?>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfBean.class);
	
	@Deprecated
	protected String[] langKeys;
	
	protected GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo;
	protected JeeslTranslationBean<L,D,LOC> bTranslation;
	
	protected final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore;
	protected final GeoMetaFactoryBuilder<L,D,DS,VP,JSON,JQ,JL> fbMeta;
	protected final GeoSldFactoryBuilder<L,D,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld;
	
	protected final EjbLangFactory<L> efLang;
	protected final EjbDescriptionFactory<D> efDescription;
	
	protected final EjbGeoCategoryFactory<L,D,CATEGORY> efCategory;
	protected final EjbGeoServiceFactory<L,D,SERVICE> efService;
	protected final EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER> efLayer;
	protected final EjbGeoMapFactory<L,D,MAP> efMap;
	protected final EjbGeoScaleFactory<L,D,SCALE> efScale;
	protected final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	protected final EjbGeoViewPortFactory<VP> efViewPort;
	
	protected final EjbGeoDataSourceFactory<L,D,DS> efDs;
	protected final EjbGeoSldTemplateFactory<SLDTEMPLATE> efTemplate;
	protected final EjbGeoSldFactory<SLDTYPE,SLD> efSld;
	
	public AbstractGeoJsfBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
							GeoMetaFactoryBuilder<L,D,DS,VP,JSON,JQ,JL> fbMeta,
							GeoSldFactoryBuilder<L,D,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		this.fbCore=fbCore;
		this.fbMeta=fbMeta;
		this.fbSld=fbSld;

	
		efLang = EjbLangFactory.instance(fbCore.getClassL());
	    efDescription = EjbDescriptionFactory.factory(fbCore.getClassD());
	    	
	    efCategory = fbCore.ejbCategory();
	    efService = fbCore.ejbService();
	    efLayer = fbCore.ejbLayer();
	    efMap = fbCore.ejbMap();
	    efScale = fbCore.ejbScale();
	    efView = fbCore.ejbView();
	    efViewPort = fbMeta.ejbViewPort();
    	
		efDs = fbMeta.ejbDs();
		efSld = fbSld.ejbSld();
		efTemplate = fbSld.efTemplate();
	}
	
	protected void postConstructGeojsf(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo)
	{
		this.bTranslation=bTranslation;
		this.langKeys=bTranslation.getLangKeys().toArray(new String[0]);
		this.fGeo=fGeo;
		postInit();
	}
	
	protected void postInit(){}
}