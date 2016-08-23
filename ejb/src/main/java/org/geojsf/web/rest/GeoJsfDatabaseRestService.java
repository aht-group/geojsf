package org.geojsf.web.rest;

import java.util.HashSet;
import java.util.Set;

import org.geojsf.factory.xml.geojsf.XmlCategoryFactory;
import org.geojsf.factory.xml.geojsf.XmlLayerFactory;
import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.factory.xml.geojsf.XmlViewFactory;
import org.geojsf.factory.xml.geojsf.meta.XmlViewPortFactory;
import org.geojsf.factory.xml.geojsf.sld.XmlSldTemplateFactory;
import org.geojsf.factory.xml.openlayers.XmlMapFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
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
import org.geojsf.interfaces.rest.db.GeoJsfDatabaseImportRest2;
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

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.aht.XmlContainerFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Container;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class GeoJsfDatabaseRestService <L extends UtilsLang,
										D extends UtilsDescription,
										G extends UtilsGraphic<L,D,G,GT,GS>,
										GT extends UtilsStatus<GT,L,D>,
										GS extends UtilsStatus<GS,L,D>,
										CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
										SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
										SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
										TMP extends UtilsStatus<TMP,L,D>
										>
	implements GeoJsfDatabaseExportRest,GeoJsfDatabaseImportRest2
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDatabaseRestService.class);
	
	private GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	private final Class<L> cLang;
	private final Class<D> cDescription;
	
	private final Class<SERVICE> cService;
	private final Class<CATEGORY> cCategory;
	private final Class<LAYER> cLayer;
	private final Class<MAP> cMap;
	private final Class<VP> cViewPort;
	private final Class<SLDTYPE> cSldType;
	
	private final Class<SLDTEMPLATE> cSldTemplate;
	private final Class<TMP> cTypeMultiPolygon;
	
	private XmlStatusFactory xfStatus;
	
	private GeoJsfDatabaseRestService(GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo,final Class<L> cLang, final Class<D> cDescription,final Class<CATEGORY> cCategory,final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VP> cViewPort, final Class<SLDTYPE> cSldType, final Class<SLDTEMPLATE> cSldTemplate, final Class<TMP> cTypeMultiPolygon)
	{
		this.fGeo=fGeo;
		
		this.cLang=cLang;
		this.cDescription=cDescription;
		
		this.cCategory=cCategory;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cMap=cMap;
		this.cViewPort=cViewPort;
		this.cSldType=cSldType;
		
		this.cSldTemplate=cSldTemplate;
		this.cTypeMultiPolygon=cTypeMultiPolygon;
		
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					G extends UtilsGraphic<L,D,G,GT,GS>,
					GT extends UtilsStatus<GT,L,D>,
					GS extends UtilsStatus<GS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					TMP extends UtilsStatus<TMP,L,D>>
		GeoJsfDatabaseRestService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,TMP>
		factory(GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo, final Class<L> cLang, final Class<D> cDescription,final Class<CATEGORY> cCategory, final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<MAP> cMap, final Class<VP> cViewPort,final Class<SLDTYPE> cSldType, final Class<SLDTEMPLATE> cSldTemplate, final Class<TMP> cTypeMultiPolygon)
	{
		return new GeoJsfDatabaseRestService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,TMP>(fGeo,cLang,cDescription,cCategory,cService,cLayer,cMap,cViewPort,cSldType,cSldTemplate,cTypeMultiPolygon);
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
	
	@Override public Container exportGeoJsfSldTemplateTypes()
	{
		Container aht = XmlContainerFactory.build();
		for(SLDTYPE ejb : fGeo.allOrderedPosition(cSldType)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}
	
	@Override public Container exportTypesMultiPolygon()
	{
		Container aht = XmlContainerFactory.build();
		for(TMP ejb : fGeo.allOrderedPosition(cTypeMultiPolygon)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	@Override public Repository exportSldTemplates()
	{
		logger.info("Export GeoJsf "+SldTemplate.class.getSimpleName());
		XmlSldTemplateFactory<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE> f = new XmlSldTemplateFactory<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(GeoJsfQuery.get(GeoJsfQuery.Key.sldTemplate, null));
		Repository repository = new Repository();
		
		for(SLDTEMPLATE template : fGeo.all(cSldTemplate))
		{
			repository.getSldTemplate().add(f.build(template));
		}
		return repository;
	}
	
	
	@Override public DataUpdate importGeoJsfSldTypes(Container types){return importStatus(cTypeMultiPolygon,types,null);}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Container container, Class<P> cParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, cLang, cDescription));
        asdi.setFacade(fGeo);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(),clStatus,cLang,cParent);
        asdi.deleteUnusedStatus(clStatus,cLang,cDescription);
        return dataUpdate;
    }
}