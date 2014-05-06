package org.geojsf.util.db.init;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbGeoServerInit <L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
						MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
{
	final static Logger logger = LoggerFactory.getLogger(DbGeoServerInit.class);
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    private final Class<MAP> cMap;
    private final Class<VIEW> cView;
   
    private UtilsFacade fUtils;
    private GeoJsfFacade fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbGeoServerInit(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, UtilsFacade fUtils, GeoJsfFacade fGeo)
	{       
    	this.cL = cL;
    	this.cD = cD;
    	this.cService = cService;
        this.cLayer = cLayer;
        this.cMap = cMap;
        this.cView = cView;
        
        this.fUtils = fUtils;
        this.fGeo = fGeo;
        
        ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
					MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
		DbGeoServerInit<L,D,SERVICE,LAYER,MAP,VIEW>
		factory(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, UtilsFacade fUtils, GeoJsfFacade fGeo)
	{
		return new DbGeoServerInit<L,D,SERVICE,LAYER,MAP,VIEW>(cL,cD,cService,cLayer,cMap,cView,fUtils,fGeo);
	}

	public void iuGeoJsf(Repository repository, Layers layers, Maps maps, String[] langKeys) throws UtilsConfigurationException
	{
		logger.info("Importing/Updating GeoJSF "+Service.class.getSimpleName()+"/"+Layer.class.getSimpleName()+"/"+Map.class.getSimpleName());
		
		DbServiceInit<L,D,SERVICE,LAYER,MAP,VIEW> serviceInit = DbServiceInit.factory(cL,cD,cService,fUtils);
		DbLayerInit<L,D,SERVICE,LAYER,MAP,VIEW> layerInit = DbLayerInit.factory(cL,cD,cService,cLayer,fUtils);
		DbMapInit<L,D,SERVICE,LAYER,MAP,VIEW> viewInit = DbMapInit.factory(cL,cD,cLayer,cMap,cView,fUtils,fGeo);
		
		serviceInit.iuServices(repository);
		layerInit.iuLayers(layers, langKeys);
		viewInit.iuMaps(maps);
	}
}