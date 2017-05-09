package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.event.MapAjaxEvent;
import org.geojsf.factory.ejb.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.factory.ejb.EjbGeoViewPortFactory;
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

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractMapServiceBean <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractMapServiceBean.class);
	
	private String[] langKeys;
	
	private GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoCategoryFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efCategory;
	protected EjbGeoServiceFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efService;
	protected EjbGeoLayerFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efLayer;
	protected EjbGeoMapFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efMap;
	protected EjbGeoViewFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efView;
	protected EjbGeoViewPortFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efViewPort;
	
	private Class<CATEGORY> cCategory;
	private Class<SERVICE> cService;
	private Class<LAYER> cLayer;
	
	protected List<SERVICE> services; public List<SERVICE> getServices() {return services;}
	protected List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	protected List<LAYER> layers; public List<LAYER> getLayers() {return layers;}
	protected List<SLD> slds; public List<SLD> getSlds() {return slds;}
	
	protected MAP map; public MAP getMap() {return map;}
	protected CATEGORY category; public CATEGORY getCategory(){return category;} public void setCategory(CATEGORY category){this.category = category;}
	protected VP viewPort; public VP getViewPort(){return viewPort;} public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	protected SERVICE service; public SERVICE getService() {return service;} public void setService(SERVICE service) {this.service = service;}
	protected LAYER layer; public LAYER getLayer() {return layer;} public void setLayer(LAYER layer) {this.layer = layer;}
	
	public void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo, final Class<L> cLang, final Class<D> clDescription, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cViewPort, final Class<SLD> cSld)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		this.cCategory=cCategory;
		this.cService=cService;
		this.cLayer=cLayer;
		efLang = EjbLangFactory.createFactory(cLang);
    	efDescription = EjbDescriptionFactory.createFactory(clDescription);
    	efCategory = EjbGeoCategoryFactory.factory(cCategory);
    	efService = EjbGeoServiceFactory.factory(cService);
    	efLayer = EjbGeoLayerFactory.factory(cLang,cLayer);
    	efMap = EjbGeoMapFactory.factory(cLang, cMap);
    	efView = EjbGeoViewFactory.factory(cView);
    	efViewPort = EjbGeoViewPortFactory.factory(cViewPort);
    	
    	slds = fGeo.fLibrarySlds();
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(cService);
	}
	
	public void addService() throws UtilsConstraintViolationException
	{
		service = efService.build(null, null);
		
		service.setName(efLang.createEmpty(langKeys));
		service.setDescription(efDescription.createEmpty(langKeys));
		
		layer=null;
		category = null;
		viewPort=null;
	}

	public void selectService() throws UtilsNotFoundException
	{
		service = fGeo.load(cService,service);
		logger.info("selectService "+service);
		layer=null;
		category = null;
		viewPort=null;
	}
	
	public void rm(SERVICE item)
	{
		logger.info("rm "+item);
		try
		{
			fGeo.rm(item);
			layer=null;
			service=null;
			reloadServices();
		}
		catch (UtilsConstraintViolationException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveService() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("saveService "+service);
		service = fGeo.save(service);
		reloadServices();
	}
	
	public void cancelService() throws UtilsConstraintViolationException, UtilsLockingException
	{
		service=null;
		category=null;
		viewPort=null;
	}
	
	// CATEGORY
	protected void reloadCategories()
	{
		categories = fGeo.allOrderedPositionVisible(cCategory);
	}
	
	public void addCategory() throws UtilsConstraintViolationException
	{
		logger.info("ADD CAtegory");
		category = efCategory.build(null);
		
		category.setName(efLang.createEmpty(langKeys));
		category.setDescription(efDescription.createEmpty(langKeys));
		service=null;
	}
	
	public void selectCategory() throws UtilsNotFoundException
	{
		category = fGeo.load(cCategory,category);
		logger.info("selectCategory "+category);
		reloadLayers();
		layer=null;
		service=null;
		viewPort=null;
	}
	
	public void rm(CATEGORY item)
	{
		logger.info("rm "+item);
		try
		{
			fGeo.rm(item);
			reloadCategories();
			category=null;
			service=null;
		}
		catch (UtilsConstraintViolationException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveCategory() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("saveCategory "+category);
		category = fGeo.save(category);
		reloadCategories();
		service=null;
	}
	
	public void cancelCategory()
	{
		category=null;
		service=null;
		layer=null;
		viewPort=null;
	}
	
	protected void reorderCategories() throws UtilsConstraintViolationException, UtilsLockingException
	{
		PositionListReorderer.reorder(fGeo, categories);
	}
	
	protected void reloadLayers()
	{
		category = fGeo.load(cCategory, category);
		layers = fGeo.allOrderedPositionVisibleParent(cLayer, category);;
		logger.info("#Layer:"+layers.size());
	}
	
	public void addLayer() throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info("addLayer ");
		layer = efLayer.build(null, service,category,langKeys);
		
		layer.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void selectLayer() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("selectLayer "+layer);
		layer = fGeo.load(cLayer,layer);
		if(layer.getViewPort()==null){addViewPort();}
		else{viewPort=layer.getViewPort();}
		
		VIEW view = efView.build();
		view.setLayer(layer);
		view.setVisible(true);
		
		map = efMap.build();
		map.getViews().add(view);
	}
	
	public void saveLayer() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info("saveLayer "+layer);
		layer.setService(fGeo.find(cService, layer.getService()));
		layer.setCategory(fGeo.find(cCategory, layer.getCategory()));
		layer = fGeo.save(layer);
		reloadLayers();
		selectLayer();
	}
	
	public void rmLayer()
	{
		logger.info("rm "+layer);
		fGeo.rm(cLayer,layer);
		reloadLayers();
		layer=null;
		viewPort=null;
	}
	
	public void cancelLayer()
	{
		layer=null;
		viewPort=null;
	}
	
	protected void reorderLayer() throws UtilsConstraintViolationException, UtilsLockingException
	{
		PositionListReorderer.reorder(fGeo, layers);
	}
	
	// View Port
	private void addViewPort() throws UtilsConstraintViolationException, UtilsLockingException
	{
		viewPort = efViewPort.build();
		viewPort = fGeo.save(viewPort);
		layer.setViewPort(viewPort);
		layer = fGeo.update(layer);
	}
	
	public void mapMove(MapAjaxEvent evt)
	{
		logger.info("Viewport: "+evt.getViewPort().getLat() +"/" +evt.getViewPort().getLon() +" in bounds of " +evt.getViewPort().getTop() +" and " +evt.getViewPort().getBottom());	
		efViewPort.update(viewPort,evt.getViewPort());
	}
	
	public void saveViewPort() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
}