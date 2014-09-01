package org.geojsf.web.rest;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.xml.geojsf.XmlCategoryFactory;
import org.geojsf.factory.xml.geojsf.XmlLayerFactory;
import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.factory.xml.geojsf.XmlViewFactory;
import org.geojsf.factory.xml.openlayers.XmlMapFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.rest.db.GeoJsfDatabaseExportRest;
import org.geojsf.util.query.GeoJsfQuery;
import org.geojsf.xml.geojsf.Category;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfRestDatabaseExporter <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
	implements GeoJsfDatabaseExportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfRestDatabaseExporter.class);
	
	private GeoJsfUtilsFacade fGeo;
	
	private final Class<SERVICE> cService;
	private final Class<CATEGORY> cCategory;
	private final Class<LAYER> cLayer;
	private final Class<MAP> cMap;
//	private final Class<VIEW> cView;
	
	private GeoJsfRestDatabaseExporter(GeoJsfUtilsFacade fGeo,final Class<CATEGORY> cCategory,final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap)
	{
		this.fGeo=fGeo;
		this.cCategory=cCategory;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cMap=cMap;
//		this.cView=cView;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		GeoJsfRestDatabaseExporter<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>
		factory(GeoJsfUtilsFacade fGeo, final Class<CATEGORY> cCategory, final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap)
	{
		return new GeoJsfRestDatabaseExporter<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>(fGeo,cCategory,cService,cLayer,cMap);
	}

	@Override
	public Repository exportServices()
	{
		logger.info("Export "+Service.class.getSimpleName());
		Repository repository = new Repository();
		XmlServiceFactory f = new XmlServiceFactory(GeoJsfQuery.get(GeoJsfQuery.Key.service, null));
		
		for(SERVICE service : fGeo.all(cService))
		{
			repository.getService().add(f.build(service));
		}
		
		return repository;
	}
	
	@Override
	public Repository exportCategories()
	{
		logger.info("Export "+Category.class.getSimpleName());
		Repository repository = new Repository();
		
		XmlCategoryFactory f = new XmlCategoryFactory(GeoJsfQuery.get(GeoJsfQuery.Key.category));
		
		for(CATEGORY category : fGeo.all(cCategory))
		{
			repository.getCategory().add(f.build(category));
		}
		
		return repository;
	}

	@Override
	public Layers exportLayers()
	{
		logger.info("Export "+Layer.class.getSimpleName());
		Layers layers = new Layers();
		XmlLayerFactory f = new XmlLayerFactory(GeoJsfQuery.get(GeoJsfQuery.Key.layer, null));
		
		for(LAYER layer : fGeo.all(cLayer))
		{
			layer = fGeo.load(cLayer,layer);
			layers.getLayer().add(f.build(layer));
		}
		
		return layers;
	}

	@Override
	public Maps exportMaps()
	{
		logger.info("Export GeoJsf "+Map.class.getSimpleName());
		Maps maps = new Maps();
		XmlMapFactory fMap = new XmlMapFactory(GeoJsfQuery.get(GeoJsfQuery.Key.map, null));
		XmlViewFactory fView = new XmlViewFactory(GeoJsfQuery.get(GeoJsfQuery.Key.view, null));
		
		for(MAP map : fGeo.all(cMap))
		{
			map = fGeo.load(cMap,map);
			Map xml = fMap.build(map);
			
			for(VIEW view : map.getViews())
			{
				xml.getView().add(fView.build(view));
			}
			
			maps.getMap().add(xml);
		}
		
		return maps;
	}
}