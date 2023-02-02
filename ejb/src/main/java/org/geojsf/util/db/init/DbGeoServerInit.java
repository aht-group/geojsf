package org.geojsf.util.db.init;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfLayerType;
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
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Service;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbGeoServerInit <L extends JeeslLang, D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
								LT extends GeoJsfLayerType<L,D,LT,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
								SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,?>,
								JSON extends GeoJsfJsonData<L,D,JQ,JL>,
								JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
								JL extends GeoJsfLocationLevel<L,D,JL,?>
								>
{
	final static Logger logger = LoggerFactory.getLogger(DbGeoServerInit.class);
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW> fbCore;
	private final GeoMetaFactoryBuilder<L,D,DS,VP,SCALE> fbMeta;
	
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    private final Class<MAP> cMap;
    private final Class<VIEW> cView;
    private final Class<VP> cVp;
   
    private JeeslFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo;
 
    public DbGeoServerInit(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW> fbCore,
    						GeoMetaFactoryBuilder<L,D,DS,VP,SCALE> fbMeta,
    		final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cVp,JeeslFacade fUtils,
    		GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{
    	this.fbCore=fbCore;
    	this.fbMeta=fbMeta;
    	this.cL = cL;
    	this.cD = cD;
    	this.cCategory = cCategory;
    	this.cService = cService;
        this.cLayer = cLayer;
        this.cMap = cMap;
        this.cView = cView;
        this.cVp = cVp;
        
        this.fUtils = fUtils;
        this.fGeo = fGeo;
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
					LT extends GeoJsfLayerType<L,D,LT,?>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,?>,
					SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					JSON extends GeoJsfJsonData<L,D,JQ,JL>,
					JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
					JL extends GeoJsfLocationLevel<L,D,JL,?>> 
		DbGeoServerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,JSON,JQ,JL>
		factory(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW> fbCore,
					final GeoMetaFactoryBuilder<L,D,DS,VP,SCALE> fbMeta,
				
				final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory,final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cVp,JeeslFacade fUtils,
					GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{
		return new DbGeoServerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,JSON,JQ,JL>(fbCore,fbMeta,cL,cD,cCategory,cService,cLayer,cMap,cView,cVp,fUtils,fGeo);
	}

	public void iuGeoJsf(Repository repository, Layers layers, Maps maps, String[] langKeys) throws UtilsConfigurationException
	{
		logger.info("Importing/Updating GeoJSF "+Service.class.getSimpleName()+"/"+Layer.class.getSimpleName()+"/"+Map.class.getSimpleName());
		
		DbServiceInit<L,D,SERVICE> serviceInit = DbServiceInit.factory(cL,cD,cService,fUtils);
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> layerInit = DbLayerInit.factory(fbCore,fbMeta,cCategory,cService,cLayer,cVp,fUtils,fGeo);
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> viewInit = DbMapInit.factory(fbCore,fbMeta,cLayer,cMap,cView,cVp,fUtils,fGeo);
		
		serviceInit.iuServices(repository);
		layerInit.iuLayers(layers, langKeys);
		viewInit.iuMaps(maps);
	}
}