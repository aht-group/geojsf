package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.geojsf.event.MapAjaxEvent;
import org.geojsf.factory.ejb.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.factory.ejb.EjbGeoViewPortFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMapServiceBean <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapServiceBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efCategory;
	protected EjbGeoServiceFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efService;
	protected EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efLayer;
	protected EjbGeoMapFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efMap;
	protected EjbGeoViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efView;
	protected EjbGeoViewPortFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efViewPort;
	
	protected GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fGeo;
	
	protected MAP map;
	public MAP getMap() {return map;}
	
	protected List<SERVICE> services;
	public List<SERVICE> getServices() {return services;}
	
	protected List<CATEGORY> categories;
	public List<CATEGORY> getCategories() {return categories;}
	
	protected List<LAYER> layers;
	public List<LAYER> getLayers() {return layers;}
	
	protected SERVICE service;
	public SERVICE getService() {return service;}
	public void setService(SERVICE service) {this.service = service;}
	
	protected CATEGORY category;
	public CATEGORY getCategory(){return category;}
	public void setCategory(CATEGORY category){this.category = category;}
	
	protected VP viewPort;
	public VP getViewPort(){return viewPort;}
	public void setViewPort(VP viewPort){this.viewPort = viewPort;}

	private String[] langKeys;
	private Class<CATEGORY> cCategory;
	private Class<SERVICE> cService;
	private Class<LAYER> cLayer;
	
	public void initSuper(String[] langKeys, final Class<L> cLang, final Class<D> clDescription, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView, final Class<VP> cViewPort)
	{
		this.langKeys=langKeys;
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
	}
	
	//SERVICES
	protected void reloadServices()
	{
		services = fGeo.all(cService);
	}
	
	public void addService() throws UtilsIntegrityException, InstantiationException, IllegalAccessException
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
		catch (UtilsIntegrityException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveService() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info("saveService "+service);
		service = fGeo.save(service);
		reloadServices();
	}
	
	public void cancelService() throws UtilsContraintViolationException, UtilsLockingException
	{
		service=null;
		category=null;
		viewPort=null;
	}
	
	// CATEGORY
	protected void reloadCategories()
	{
		categories = fGeo.all(cCategory);
	}
	
	public void addCategory() throws UtilsIntegrityException, InstantiationException, IllegalAccessException
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
		reloadLayer();
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
		catch (UtilsIntegrityException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveCategory() throws UtilsContraintViolationException, UtilsLockingException
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
	
	// LAYER
	protected LAYER layer;
	public LAYER getLayer() {return layer;}
	public void setLayer(LAYER layer) {this.layer = layer;}
	
	protected void reloadLayer()
	{
		category = fGeo.load(cCategory, category);
		layers = category.getLayer();
		logger.info("#Layer:"+layers.size());
	}
	
	public void addLayer() throws UtilsIntegrityException, UtilsNotFoundException, InstantiationException, IllegalAccessException
	{
		logger.info("addLayer ");
		layer = efLayer.build(null, service,category,langKeys);
		
		layer.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void selectLayer() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
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
	
	public void saveLayer() throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info("saveLayer "+layer);
		layer.setService(fGeo.find(cService, layer.getService()));
		layer.setCategory(fGeo.find(cCategory, layer.getCategory()));
		layer = fGeo.save(layer);
		reloadLayer();
		selectLayer();
	}
	
	public void rm(LAYER item)
	{
		logger.info("rm "+item);
		fGeo.rm(cLayer,item);
		reloadLayer();
		layer=null;
		viewPort=null;
	}
	
	public void cancelLayer()
	{
		layer=null;
		viewPort=null;
	}
	
	// View Port
	private void addViewPort() throws UtilsContraintViolationException, UtilsLockingException
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
	
	public void saveViewPort() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
}