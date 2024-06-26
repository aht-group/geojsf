package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.core.EjbGeoServiceFactory;
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
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.jsf.event.MapAjaxEvent;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.web.util.AbstractLogMessage;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.jsf.jx.util.FacesContextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated //Use GeojsfSettingsLayerController instead
public class AbstractMapServiceBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
									LT extends GeoJsfLayerType<L,D,LT,?>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									SCALE extends GeoJsfScale<L,D>, 
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									DS extends GeoJsfDataSource<L,D,LAYER>,
									SLDTEMPLATE extends GeoJsfSldXml<L,D,SLD>,
									SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE,?,?>,
									RULE extends GeoJsfSldRule<L,D,?>,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<L,D,JL,?>>
	extends AbstractGeoJsfBean<L,D,LOC,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapServiceBean.class);
	
	protected final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	protected final EjbGeoViewPortFactory<VP> efViewPort;
	protected final EjbGeoCategoryFactory<L,D,CATEGORY> efCategory;
	protected final EjbGeoServiceFactory<L,D,SERVICE> efService;
	
	protected List<SERVICE> services; public List<SERVICE> getServices() {return services;}
	protected List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	protected List<LAYER> layers; public List<LAYER> getLayers() {return layers;}
	protected List<SLD> slds; public List<SLD> getSlds() {return slds;}
	private List<VIEW> views; public List<VIEW> getViews() {return views;}

	protected MAP map; public MAP getMap() {return map;}
	protected CATEGORY category; public CATEGORY getCategory(){return category;} public void setCategory(CATEGORY category){this.category = category;}
	protected VP viewPort; public VP getViewPort(){return viewPort;} public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	protected SERVICE service; public SERVICE getService() {return service;} public void setService(SERVICE service) {this.service = service;}
	protected LAYER layer; public LAYER getLayer() {return layer;} public void setLayer(LAYER layer) {this.layer = layer;}
		
	public AbstractMapServiceBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
									GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
									GeoSldFactoryBuilder<L,D,LAYER,SLDTEMPLATE,SLD,SLDTYPE,RULE,?,?> fbSld)
	{
		super(fbCore);
	    efCategory = fbCore.ejbCategory();
	    efService = fbCore.ejbService();
	    efView = fbCore.ejbView();
	    efViewPort = fbMeta.ejbViewPort();
	}
	
	public void postConstructService(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo,
									GeoSldFacade<L,D,SLDTEMPLATE,SLD,SLDTYPE,RULE> fSld)
	{
		super.postConstructGeojsf(bTranslation,bMessage,fGeo);  	
		slds = fSld.fLibrarySlds();
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(fbCore.getClassService());
	}
	
	public void addService() throws JeeslConstraintViolationException
	{
		service = efService.build(null, null);
		
		service.setName(efLang.createEmpty(langKeys));
		service.setDescription(efDescription.createEmpty(langKeys));
		
		layer=null;
		category = null;
		viewPort=null;
	}

	public void selectService() throws JeeslNotFoundException
	{
		service = fGeo.load(service);
		service = efLang.persistMissingLangs(fGeo,bTranslation.getLocales(),service);
		service = efDescription.persistMissingLangs(fGeo,bTranslation.getLocales(),service);
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
		catch (JeeslConstraintViolationException e)
		{
			FacesContextMessage.instance().warn("WARN", "uieServiceWithLayer");
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
		category = efCategory.build(null);
		
		category.setName(efLang.buildEmpty(bTranslation.getLocales()));
		category.setDescription(efDescription.buildEmpty(bTranslation.getLocales()));
		
		service=null;
	}
	
	public void selectCategory() throws JeeslNotFoundException
	{
		category = fGeo.load(category);
		category = efLang.persistMissingLangs(fGeo,bTranslation.getLocales(),category);
		category = efDescription.persistMissingLangs(fGeo,bTranslation.getLocales(),category);
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
			FacesContextMessage.instance().warn("WARN", "uieServiceWithLayer");
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
	
	protected void reorderCategories() throws JeeslConstraintViolationException, JeeslLockingException
	{
		PositionListReorderer.reorder(fGeo, categories);
	}
	
	protected void reloadLayers()
	{
		category = fGeo.load(category);
		layers = fGeo.allOrderedPositionVisibleParent(fbCore.getClassLayer(), category);;
		logger.info("#Layer:"+layers.size());
	}
	
	public void addLayer() throws JeeslConstraintViolationException, JeeslNotFoundException
	{
		logger.info("addLayer ");
		layer = efLayer.build(null, service,category,langKeys);
		
		layer.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void selectLayer() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("selectLayer "+layer);
		layer = fGeo.load(layer);
		layer = efLang.persistMissingLangs(fGeo,bTranslation.getLocales(),layer);
		layer = efDescription.persistMissingLangs(fGeo,bTranslation.getLocales(),layer);
		if(layer.getViewPort()==null){addViewPort();}
		else{viewPort=layer.getViewPort();}
		
		VIEW view = efView.build();
		view.setLayer(layer);
		view.setVisible(true);
		
		map = efMap.build();
		map.getViews().add(view);
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
	
	public void reorderLayer() throws JeeslConstraintViolationException, JeeslLockingException{PositionListReorderer.reorder(fGeo, layers);}
	
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
}