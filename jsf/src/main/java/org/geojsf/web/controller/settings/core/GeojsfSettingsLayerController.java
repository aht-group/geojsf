package org.geojsf.web.controller.settings.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfLayerType;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfEcql;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.jsf.event.MapAjaxEvent;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.web.AbstractJeeslWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfSettingsLayerController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LT,VP,?,SLD>,
									LT extends GeoJsfLayerType<L,D,LT,?>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									ECQL extends GeoJsfEcql<LAYER,LE,LA>,
									LE extends JeeslRevisionEntity<L,D,?,?,LA,?>,
									LA extends JeeslRevisionAttribute<L,D,LE,?,?>,
									SLD extends GeoJsfSld<L,D,?,?,?,?,?>>
			extends AbstractJeeslWebController<L,D,LOC>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(GeojsfSettingsLayerController.class);
	
	private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,?> fGeo;
	private JeeslIoRevisionFacade<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fRevision;
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,VIEW> fbCore;
	private final GeoMetaFactoryBuilder<L,D,LAYER,?,VP,?,ECQL> fbMeta;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fbLabel;
	
	private final EjbGeoMapFactory<L,D,MAP> efMap;
	private final EjbGeoViewPortFactory<VP> efViewPort;
	private final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	
	private List<SERVICE> services; public List<SERVICE> getServices() {return services;}
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private final List<LAYER> layers; public List<LAYER> getLayers() {return layers;}
	private final List<LT> layerTypes; public List<LT> getLayerTypes() {return layerTypes;}
	private final List<ECQL> ecqls; public List<ECQL> getEcqls() {return ecqls;}
	private final List<VIEW> views; public List<VIEW> getViews() {return views;}
	private List<SLD> slds; public List<SLD> getSlds() {return slds;}
	private final List<LE> entities; public List<LE> getEntities() {return entities;}
	private final List<LA> attributes; public List<LA> getAttributes() {return attributes;}
	
	private SERVICE service; public SERVICE getService() {return service;} public void setService(SERVICE service) {this.service = service;}
	private CATEGORY category; public CATEGORY getCategory() {return category;} public void setCategory(CATEGORY category){this.category = category;}
	private LAYER layer; public LAYER getLayer() {return layer;} public void setLayer(LAYER layer) {this.layer = layer;}
	private MAP map; public MAP getMap() {return map;}
	private VP viewPort; public VP getViewPort() {return viewPort;} public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	private ECQL ecql; public ECQL getEcql() {return ecql;} public void setEcql(ECQL ecql) {this.ecql = ecql;}
	
	public GeojsfSettingsLayerController(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,VIEW> fbCore,
											GeoMetaFactoryBuilder<L,D,LAYER,?,VP,?,ECQL> fbMeta,
											IoRevisionFactoryBuilder<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fbLabel)
	{
		super(fbCore.getClassL(),fbCore.getClassD());
		this.fbCore = fbCore;
		this.fbMeta = fbMeta;
		this.fbLabel = fbLabel;
		
		efMap = fbCore.ejbMap();
		efView = fbCore.ejbView();
		efViewPort = fbMeta.ejbViewPort();
		
		layers = new ArrayList<>();
		views = new ArrayList<>();
		layerTypes = new ArrayList<>();
		ecqls = new ArrayList<>();
		entities = new ArrayList<>();
		attributes = new ArrayList<>();
	}
	
	public void postConstructService(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
										GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,?> fGeo,
										GeoSldFacade<L,D,?,SLD,?,?> fSld,
										JeeslIoRevisionFacade<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fRevision
										)
	{
		super.postConstructWebController(lp,bMessage);
		this.fGeo=fGeo;
		this.fRevision=fRevision;

		slds = fSld.fLibrarySlds();
		
		layerTypes.addAll(fGeo.allOrderedPositionVisible(fbCore.getClassLayerType()));
		entities.addAll(fGeo.allOrderedPositionVisible(fbLabel.getClassEntity()));
		
		this.reloadServices();
		this.reloadCategories();
	}
	
	private void reset(boolean rViews, boolean rEcql)
	{
		if(rViews) {views.clear();}
		if(rEcql) {ecql = null;}
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(fbCore.getClassService());
	}
	
	public void addService() throws JeeslConstraintViolationException
	{
		service = fbCore.ejbService().build(null, null);
		service.setName(efLang.buildEmpty(lp.getLocales()));
		service.setDescription(efDescription.buildEmpty(lp.getLocales()));
		
		layer=null;
		category = null;
		viewPort=null;
	}

	public void selectService() throws JeeslNotFoundException
	{
		service = fGeo.load(service);
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
		
		category.setName(efLang.buildEmpty(lp.getLocales()));
		category.setDescription(efDescription.buildEmpty(lp.getLocales()));
		
		service=null;
	}
	
	public void selectCategory() throws JeeslNotFoundException
	{
		category = fGeo.load(category);
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
//		category = fGeo.load(category);
//		layers = fGeo.allOrderedPositionVisibleParent(fbCore.getClassLayer(), category);;
		
		layers.clear();
		layers.addAll(fGeo.allForParent(fbCore.getClassLayer(), category));
		logger.info(fbCore.getClassLayer().getSimpleName()+": "+layers.size());
	}
	
	public void addLayer() throws JeeslConstraintViolationException, JeeslNotFoundException
	{
		logger.info("addLayer ");
		layer = fbCore.ejbLayer().build(null, service,category);
		layer.setName(efLang.buildEmpty(lp.getLocales()));
		layer.setDescription(efDescription.buildEmpty(lp.getLocales()));
	}
	
	public void selectLayer() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		this.reset(true,true);
		logger.info(AbstractLogMessage.selectEntity(layer));
		layer = fGeo.load(layer);
		layer = efLang.persistMissingLangs(fGeo,lp.getLocales(),layer);
		layer = efDescription.persistMissingLangs(fGeo,lp.getLocales(),layer);
		
		this.reloadLayer();
		this.reloadEcqls();
		
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
		this.reset(true,false);
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
	
	// ECQL
	private void reloadEcqls()
	{
		ecqls.clear();
		ecqls.addAll(fGeo.allForParent(fbMeta.getClassEcql(), layer));
	}
	
	public void addEcql() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.createEntity(fbMeta.getClassEcql()));
		ecql = fbMeta.ejbEcql().build(layer);
		ecql.setEntity(entities.get(0));
		EjbPositionFactory.next(ecql,ecqls);
		this.reloadAttributes();
	}
	public void reloadAttributes()
	{
		attributes.clear();
		if(Objects.nonNull(ecql.getEntity()))
		{
			ecql.setEntity(fRevision.find(fbLabel.getClassEntity(), ecql.getEntity()));
			ecql.setEntity(fRevision.load(fbLabel.getClassEntity(), ecql.getEntity()));
			attributes.addAll(ecql.getEntity().getAttributes());
		}
		logger.info(AbstractLogMessage.reloaded(fbLabel.getClassAttribute(),attributes));
	}
	public void reloadAttribute()
	{
		if(Objects.nonNull(ecql.getAttribute()))
		{
			ecql.setAttribute(fRevision.find(fbLabel.getClassAttribute(), ecql.getAttribute()));
		}
	}
	public void saveEcql() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(ecql));
		ecql = fGeo.save(ecql);
		this.reloadEcqls();
	}
	public void selectEcql() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(ecql));
		this.reloadAttributes();
	}
	public void deleteEcql() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.deleteEntity(ecql));
		fGeo.rm(ecql);
		ecql = null;
		this.reloadAttributes();
	}
	
	public void reorderCategories() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fGeo, categories);}
	public void reorderLayer() throws JeeslConstraintViolationException, JeeslLockingException{PositionListReorderer.reorder(fGeo, layers);}
}