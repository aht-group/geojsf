package org.geojsf.web.controller.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.jsf.event.MapAjaxEvent;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.web.AbstractJeeslWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfSettingsLayerController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,?,SLD>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									SLD extends GeoJsfSld<L,D,?,?,?>>
			extends AbstractJeeslWebController<L,D,LOC>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(GeojsfSettingsLayerController.class);
	
	private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,?,VIEW,VP,?> fGeo;
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,?,VIEW,VP> fbCore;
	
	private final EjbGeoMapFactory<L,D,MAP> efMap;
	private final EjbGeoViewPortFactory<VP> efViewPort;
	private final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	
	protected List<SERVICE> services; public List<SERVICE> getServices() {return services;}
	protected List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	protected List<LAYER> layers; public List<LAYER> getLayers() {return layers;}
	private final List<VIEW> views; public List<VIEW> getViews() {return views;}
	protected List<SLD> slds; public List<SLD> getSlds() {return slds;}

	protected MAP map; public MAP getMap() {return map;}
	protected CATEGORY category; public CATEGORY getCategory() {return category;} public void setCategory(CATEGORY category){this.category = category;}
	protected VP viewPort; public VP getViewPort() {return viewPort;} public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	protected SERVICE service; public SERVICE getService() {return service;} public void setService(SERVICE service) {this.service = service;}
	protected LAYER layer; public LAYER getLayer() {return layer;} public void setLayer(LAYER layer) {this.layer = layer;}
		
	public GeojsfSettingsLayerController(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,?,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,?,VP,?,?,?> fbMeta)
	{
		super(fbCore.getClassL(),fbCore.getClassD());
		this.fbCore = fbCore;
		
		efMap = fbCore.ejbMap();
		efView = fbCore.ejbView();
		efViewPort = fbMeta.ejbViewPort();
		
		views = new ArrayList<>();
	}
	
	public void postConstructService(GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,?,VIEW,VP,?> fGeo,
										GeoSldFacade<L,D,?,?,SLD,?> fSld,				
										JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage)
	{
		super.postConstructWebController(lp,bMessage);
		this.fGeo=fGeo;

		slds = fSld.fLibrarySlds();
		
		this.reloadServices();
		this.reloadCategories();
	}
	
	private void reset(boolean rViews)
	{
		if(rViews) {views.clear();}
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(fbCore.getClassService());
	}
	
	public void addService() throws JeeslConstraintViolationException
	{
		service = fbCore.ejbService().build(null, null);
		
		service.setName(efLang.createEmpty(lp.getLocales()));
		service.setDescription(efDescription.createEmpty(lp.getLocales()));
		
		layer=null;
		category = null;
		viewPort=null;
	}

	public void selectService() throws JeeslNotFoundException
	{
		service = fGeo.load(fbCore.getClassService(),service);
		service = efLang.persistMissingLangs(fGeo,lp.getLocales(),service);
		service = efDescription.persistMissingLangs(fGeo,lp.getLocales(),service);
		logger.info("selectService "+service);
		layer=null;
		category = null;
		viewPort=null;
	}
	
	public void deleteService()
	{
		logger.info("rm "+service);
		try
		{
			fGeo.rm(service);
			layer=null;
			service=null;
			reloadServices();
		}
		catch (JeeslConstraintViolationException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveService() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("saveService "+service);
		service = fGeo.save(service);
		reloadServices();
	}
	
	public void cancelService() throws JeeslConstraintViolationException, JeeslLockingException
	{
		service=null;
		category=null;
		viewPort=null;
	}
	
	// CATEGORY
	protected void reloadCategories()
	{
		categories = fGeo.allOrderedPositionVisible(fbCore.getClassCategory());
	}
	
	public void addCategory() throws JeeslConstraintViolationException
	{
		logger.info("ADD CAtegory");
		category = fbCore.ejbCategory().build(null);
		
		category.setName(efLang.createEmpty(lp.getLocales()));
		category.setDescription(efDescription.createEmpty(lp.getLocales()));
		
		service=null;
	}
	
	public void selectCategory() throws JeeslNotFoundException
	{
		category = fGeo.load(fbCore.getClassCategory(),category);
		category = efLang.persistMissingLangs(fGeo,lp.getLocales(),category);
		category = efDescription.persistMissingLangs(fGeo,lp.getLocales(),category);
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
		catch (JeeslConstraintViolationException e)
		{
			FacesContextMessage.warn("WARN", "uieServiceWithLayer");
		}
	}
	
	public void saveCategory() throws JeeslConstraintViolationException, JeeslLockingException
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
	
	protected void reloadLayers()
	{
		category = fGeo.load(fbCore.getClassCategory(), category);
		layers = fGeo.allOrderedPositionVisibleParent(fbCore.getClassLayer(), category);;
		logger.info("#Layer:"+layers.size());
	}
	
	public void addLayer() throws JeeslConstraintViolationException, JeeslNotFoundException
	{
		logger.info("addLayer ");
		layer = fbCore.ejbLayer().build(null, service,category);
		layer.setName(efLang.createEmpty(lp.getLocales()));
		layer.setDescription(efDescription.createEmpty(lp.getLocales()));
	}
	
	public void selectLayer() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("selectLayer "+layer);
		layer = fGeo.load(fbCore.getClassLayer(),layer);
		layer = efLang.persistMissingLangs(fGeo,lp.getLocales(),layer);
		layer = efDescription.persistMissingLangs(fGeo,lp.getLocales(),layer);
		
		reloadLayer();
		
		if(layer.getViewPort()==null){addViewPort();}
		else{viewPort=layer.getViewPort();}
		
		VIEW view = efView.build();
		view.setLayer(layer);
		view.setVisible(true);
		
		map = efMap.build();
		map.getViews().add(view);
	}
	
	private void reloadLayer()
	{
		this.reset(true);
		views.addAll(fGeo.fGeoViews(layer));
	}
	
	public void saveLayer() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info("saveLayer "+layer);
		layer.setService(fGeo.find(fbCore.getClassService(), layer.getService()));
		layer.setCategory(fGeo.find(fbCore.getClassCategory(), layer.getCategory()));
		layer = fGeo.save(layer);
		reloadLayers();
		selectLayer();
	}
	
	public void rmLayer()
	{
		logger.info("rm "+layer);
		fGeo.rm(fbCore.getClassLayer(),layer);
		reloadLayers();
		layer=null;
		viewPort=null;
	}
	
	public void cancelLayer()
	{
		layer=null;
		viewPort=null;
	}
	
	// View Port
	private void addViewPort() throws JeeslConstraintViolationException, JeeslLockingException
	{
		viewPort = efViewPort.build();
		viewPort = fGeo.save(viewPort);
		layer.setViewPort(viewPort);
		layer = fGeo.save(layer);
	}
	
	public void mapMove(MapAjaxEvent evt)
	{
		logger.info("Viewport: "+evt.getViewPort().getLat() +"/" +evt.getViewPort().getLon() +" in bounds of " +evt.getViewPort().getTop() +" and " +evt.getViewPort().getBottom());	
		efViewPort.update(viewPort,evt.getViewPort());
	}
	
	public void saveViewPort() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
	
	public void reorderCategories() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fGeo, categories);}
	public void reorderLayer() throws JeeslConstraintViolationException, JeeslLockingException{PositionListReorderer.reorder(fGeo, layers);}
}