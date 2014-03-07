package org.geojsf.web.rest;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.factory.xml.geojsf.XmlViewFactory;
import org.geojsf.factory.xml.openlayers.XmlLayerFactory;
import org.geojsf.factory.xml.openlayers.XmlMapFactory;
import org.geojsf.factory.xml.openlayers.XmlServiceFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.rest.GeoJsfDatabaseRest;
import org.geojsf.util.query.OpenLayersQuery;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfRestDatabaseExporter <L extends UtilsLang,
									D extends UtilsDescription,
									SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
									LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
									MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
									VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
				implements GeoJsfDatabaseRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfRestDatabaseExporter.class);
	
	private UtilsSecurityFacade fSecurity;
	
	private final Class<SERVICE> cService;
	private final Class<LAYER> cLayer;
	private final Class<MAP> cMap;
	private final Class<VIEW> cView;
	
	private GeoJsfRestDatabaseExporter(UtilsSecurityFacade fSecurity,final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VIEW> cView)
	{
		this.fSecurity=fSecurity;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cMap=cMap;
		this.cView=cView;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
					MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>,
					LT extends UtilsStatus<L,D>>
		GeoJsfRestDatabaseExporter<L,D,SERVICE,LAYER,MAP,VIEW>
		factory(UtilsSecurityFacade fSecurity, final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VIEW> cView)
	{
		return new GeoJsfRestDatabaseExporter<L,D,SERVICE,LAYER,MAP,VIEW>(fSecurity,cService,cLayer,cMap,cView);
	}

	@Override
	public Repository exportServices()
	{
		Repository repository = new Repository();
		XmlServiceFactory f = new XmlServiceFactory(OpenLayersQuery.get(OpenLayersQuery.Key.service, null));
		
		for(SERVICE service : fSecurity.all(cService))
		{
			repository.getService().add(f.build(service));
		}
		
		return repository;
	}

	@Override
	public Layers exportLayers()
	{
		Layers layers = new Layers();
		XmlLayerFactory f = new XmlLayerFactory(OpenLayersQuery.get(OpenLayersQuery.Key.layer, null));
		
		for(LAYER layer : fSecurity.all(cLayer))
		{
			layers.getLayer().add(f.build(layer));
		}
		
		return layers;
	}

	@Override
	public Maps exportMaps()
	{
		Maps maps = new Maps();
		XmlMapFactory fMap = new XmlMapFactory(OpenLayersQuery.get(OpenLayersQuery.Key.map, null));
		XmlViewFactory fView = new XmlViewFactory(OpenLayersQuery.get(OpenLayersQuery.Key.view, null));
		
		for(MAP map : fSecurity.all(cMap))
		{
			Map xml = fMap.build(map);
			
			for(VIEW view : map.getViews())
			{
				xml.getView().add(fView.build(view));
			}
			
			maps.getMap().add(xml);
		}
		
		return maps;
	}

	public String importGeoJsf(Repository repository)
	{
		logger.error("NYI");
		return null;
	}
}