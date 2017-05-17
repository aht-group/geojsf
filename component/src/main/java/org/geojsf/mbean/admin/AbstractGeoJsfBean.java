package org.geojsf.mbean.admin;

import java.io.Serializable;

import org.geojsf.factory.ejb.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.factory.ejb.EjbGeoViewPortFactory;
import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.factory.ejb.meta.EjbGeoScaleFactory;
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
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractGeoJsfBean <L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
									CATEGORY extends GeoJsfCategory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SERVICE extends GeoJsfService<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									LAYER extends GeoJsfLayer<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									MAP extends GeoJsfMap<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SCALE extends GeoJsfScale<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									VIEW extends GeoJsfView<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									VP extends GeoJsfViewPort<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									DS extends GeoJsfDataSource<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfBean.class);
	
	protected String[] langKeys;
	protected GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	protected final Class<L> cL;
	protected final Class<D> cD;
	protected final Class<CATEGORY> cCategory;
	protected final Class<SERVICE> cService;
	protected final Class<LAYER> cLayer;
	protected final Class<MAP> cMap;
	protected final Class<SCALE> cScale;
	protected final Class<VIEW> cView;
	protected final Class<VP> cViewPort;
	protected final Class<DS> cDs;
	protected final Class<SLDTEMPLATE> cTemplate;
	protected final Class<SLDTYPE> cSldType;
	protected final Class<SLD> cSld;
	
	protected final GeoSldFactoryFactory<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> ffSld;
	protected final GeoJsfFactoryFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> ffGeo;
	
	protected final EjbLangFactory<L> efLang;
	protected final EjbDescriptionFactory<D> efDescription;
	
	protected final EjbGeoCategoryFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efCategory;
	protected final EjbGeoServiceFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efService;
	protected final EjbGeoLayerFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efLayer;
	protected final EjbGeoMapFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efMap;
	protected final EjbGeoScaleFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efScale;
	protected final EjbGeoViewFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efView;
	protected final EjbGeoViewPortFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efViewPort;
	
	protected final EjbGeoDataSourceFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efDs;
	protected final EjbGeoSldTemplateFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efTemplate;
	protected final EjbGeoSldFactory<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efSld;
	
	public AbstractGeoJsfBean(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<SCALE> cScale, final Class<VIEW> cView, final Class<VP> cViewPort, final Class<DS> cDs, final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cSldType, final Class<SLD> cSld)
	{
		this.cL = cL;
		this.cD = cD;
		this.cCategory = cCategory;
		this.cService = cService;
		this.cLayer = cLayer;
		this.cMap = cMap;
		this.cScale = cScale;
		this.cView = cView;
		this.cViewPort = cViewPort;
		this.cDs = cDs;
		this.cTemplate = cTemplate;
		this.cSldType = cSldType;
		this.cSld = cSld;
		
		ffSld = GeoSldFactoryFactory.factory(cSld);
		ffGeo = GeoJsfFactoryFactory.factory(cL,cD,cScale,cDs);
		
		efLang = EjbLangFactory.createFactory(cL);
    	efDescription = EjbDescriptionFactory.createFactory(cD);
    	
    	efCategory = EjbGeoCategoryFactory.factory(cCategory);
    	efService = EjbGeoServiceFactory.factory(cService);
    	efLayer = EjbGeoLayerFactory.factory(cL,cLayer);
    	efMap = EjbGeoMapFactory.factory(cL,cMap);
    	efScale = ffGeo.ejbScale();
    	efView = EjbGeoViewFactory.factory(cView);
    	efViewPort = EjbGeoViewPortFactory.factory(cViewPort);
    	
		efDs = ffGeo.ejbDs();
		efSld = ffSld.sld();
		efTemplate = EjbGeoSldTemplateFactory.factory(cTemplate);
	}
	
	protected void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		postInit();
	}
	
	protected void postInit(){}
}