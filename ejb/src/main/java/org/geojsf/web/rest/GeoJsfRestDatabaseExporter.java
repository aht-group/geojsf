package org.geojsf.web.rest;

import java.util.HashSet;
import java.util.Set;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.xml.geojsf.XmlCategoryFactory;
import org.geojsf.factory.xml.geojsf.XmlLayerFactory;
import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.factory.xml.geojsf.XmlSldTemplateFactory;
import org.geojsf.factory.xml.geojsf.XmlViewFactory;
import org.geojsf.factory.xml.geojsf.XmlViewPortFactory;
import org.geojsf.factory.xml.openlayers.XmlMapFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.rest.db.GeoJsfDatabaseExportRest;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Service;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.geojsf.model.xml.geojsf.ViewPorts;
import org.geojsf.util.query.GeoJsfQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfRestDatabaseExporter <L extends UtilsLang,
										D extends UtilsDescription,
										CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
										SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
										RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
										SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
										SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	implements GeoJsfDatabaseExportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfRestDatabaseExporter.class);
	
	private GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
	
	private final Class<SERVICE> cService;
	private final Class<CATEGORY> cCategory;
	private final Class<LAYER> cLayer;
	private final Class<MAP> cMap;
	private final Class<VP> cViewPort;
	private final Class<SLDTEMPLATE> cSldTemplate;
	
	private GeoJsfRestDatabaseExporter(GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo,final Class<CATEGORY> cCategory,final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VP> cViewPort,final Class<SLDTEMPLATE> cSldTemplate)
	{
		this.fGeo=fGeo;
		this.cCategory=cCategory;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cMap=cMap;
		this.cViewPort=cViewPort;
		this.cSldTemplate=cSldTemplate;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
		GeoJsfRestDatabaseExporter<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
		factory(GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo, final Class<CATEGORY> cCategory, final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VP> cViewPort,final Class<SLDTEMPLATE> cSldTemplate)
	{
		return new GeoJsfRestDatabaseExporter<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(fGeo,cCategory,cService,cLayer,cMap,cViewPort,cSldTemplate);
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
		XmlLayerFactory<D> f = new XmlLayerFactory<D>(GeoJsfQuery.get(GeoJsfQuery.Key.layer, null));
		
		for(LAYER layer : fGeo.all(cLayer))
		{
			layer = fGeo.load(cLayer,layer);
			layers.getLayer().add(f.build(layer));
		}
		
		return layers;
	}

	@Override public Maps exportMaps()
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

	@Override public ViewPorts exportViewPorts()
	{
		logger.info("Export GeoJsf "+ViewPort.class.getSimpleName());
		XmlViewPortFactory f = new XmlViewPortFactory(GeoJsfQuery.get(GeoJsfQuery.Key.viewPort, null));
		
		Set<Long> geoJsfViewports = new HashSet<Long>();
		for(MAP map : fGeo.all(cMap))
		{
			if(map.getViewPort()!=null){geoJsfViewports.add(map.getViewPort().getId());}
		}
		for(LAYER layer : fGeo.all(cLayer))
		{
			layer = fGeo.load(cLayer,layer);
			if(layer.getViewPort()!=null){geoJsfViewports.add(layer.getViewPort().getId());}
		}
		
		ViewPorts viewPorts = new ViewPorts();
		for(VP vp : fGeo.all(cViewPort))
		{
			if(!geoJsfViewports.contains(vp.getId()))
			{
				viewPorts.getViewPort().add(f.build(vp));
			}
		}
		return viewPorts;
	}

	@Override public Repository exportSldTemplates()
	{
		logger.info("Export GeoJsf "+SldTemplate.class.getSimpleName());
		XmlSldTemplateFactory<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE> f = new XmlSldTemplateFactory<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>(GeoJsfQuery.get(GeoJsfQuery.Key.sldTemplate, null));
		Repository repository = new Repository();
		
		for(SLDTEMPLATE template : fGeo.all(cSldTemplate))
		{
			repository.getSldTemplate().add(f.build(template));
		}
		return repository;
	}
}