package org.geojsf.util.db.init;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbGeoServerInit <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
{
	final static Logger logger = LoggerFactory.getLogger(DbGeoServerInit.class);
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    private final Class<MAP> cMap;
    private final Class<VIEW> cView;
    private final Class<VP> cVp;
   
    private UtilsFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbGeoServerInit(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cVp,UtilsFacade fUtils, GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> fGeo)
	{       
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
        
        ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		DbGeoServerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>
		factory(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory,final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cVp,UtilsFacade fUtils, GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> fGeo)
	{
		return new DbGeoServerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>(cL,cD,cCategory,cService,cLayer,cMap,cView,cVp,fUtils,fGeo);
	}

	public void iuGeoJsf(Repository repository, Layers layers, Maps maps, String[] langKeys) throws UtilsConfigurationException
	{
		logger.info("Importing/Updating GeoJSF "+Service.class.getSimpleName()+"/"+Layer.class.getSimpleName()+"/"+Map.class.getSimpleName());
		
		DbServiceInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> serviceInit = DbServiceInit.factory(cL,cD,cService,fUtils);
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> layerInit = DbLayerInit.factory(cL,cD,cCategory,cService,cLayer,cVp,fUtils,fGeo);
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> viewInit = DbMapInit.factory(cL,cD,cLayer,cMap,cView,cVp,fUtils,fGeo);
		
		serviceInit.iuServices(repository);
		layerInit.iuLayers(layers, langKeys);
		viewInit.iuMaps(maps);
	}
}