package org.geojsf.web.controller.settings;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
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
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.jsf.event.MapAjaxEvent;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.web.AbstractJeeslWebController;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.StringUtil;

public class GeojsfSettingsMapController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
										F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
										CATEGORY extends GeoJsfCategory<L,D,LAYER>,
										SERVICE extends GeoJsfService<L,D,LAYER>,
										LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,?>,
										MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
										SCALE extends GeoJsfScale<L,D>, 
										VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
										VP extends GeoJsfViewPort,
										DS extends GeoJsfDataSource<L,D,LAYER>>
			extends AbstractJeeslWebController<L,D,LOC>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfSettingsMapController.class);
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore;
	
	private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo;
	
	protected final EjbGeoMapFactory<L,D,MAP> efMap;
	protected final EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
	protected final EjbGeoViewPortFactory<VP> efViewPort;
	
	protected List<MAP> maps; public List<MAP> getMaps(){return maps;}
	protected List<CATEGORY> categories; public List<CATEGORY> getCategories(){return categories;}
	protected List<LAYER> layers; public List<LAYER> getLayers(){return layers;}
	
	protected MAP map; public MAP getMap() {return map;} public void setMap(MAP map) {this.map = map;}
	protected VIEW view; public VIEW getView(){return view;} public void setView(VIEW view){this.view = view;}
	
	protected CATEGORY category; public CATEGORY getCategory(){return category;} public void setCategory(CATEGORY category){this.category = category;}
	
	protected VP viewPort;
	public VP getViewPort(){return viewPort;}
	public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	
	public GeojsfSettingsMapController(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,DS,VP,?,?,?> fbMeta)
	{
		super(fbCore.getClassL(),fbCore.getClassD());
		this.fbCore=fbCore;
		
		efMap = fbCore.ejbMap();
		efView = fbCore.ejbView();
		efViewPort = fbMeta.ejbViewPort();
	}
	
	public void postConstructThematic(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
										GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo)
	{
		super.postConstructWebController(lp,bMessage);
		this.fGeo=fGeo;
		categories = fGeo.allOrderedPositionVisible(fbCore.getClassCategory());
		reloadMaps();
	}
	
	protected void reloadMaps()
	{
		maps = fGeo.allOrderedPosition(fbCore.getClassMap());
	}
	
	public void selectMap() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(map));
		
		map = efLang.persistMissingLangs(fGeo,lp.getLocales(),map);
		map = efDescription.persistMissingLangs(fGeo,lp.getLocales(),map);
		
		reloadMap();
		view=null;
	}
	
	protected void reloadMap() throws JeeslConstraintViolationException, JeeslLockingException
	{
		map = fGeo.load(fbCore.getClassMap(),map);
		if(map.getViewPort()==null){addViewPort();}
		else{viewPort=map.getViewPort();}
		logger.info(map.getViewPort().toString());
	}
	
	private void addViewPort() throws JeeslConstraintViolationException, JeeslLockingException
	{
		viewPort = efViewPort.build();
		viewPort = fGeo.save(viewPort);
		map.setViewPort(viewPort);
		map = fGeo.save(map);
	}
	
	public void addMap()
	{
		logger.info(AbstractLogMessage.addEntity(fbCore.getClassMap()));
		map = efMap.build("");
		map.setName(efLang.createEmpty(lp.getLocales()));
		map.setDescription(efDescription.createEmpty(lp.getLocales()));
	}
	
	public void saveMap() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(map));
		if(map.getCategory()!=null) {map.setCategory(fGeo.find(fbCore.getClassCategory(), map.getCategory()));}
		map = fGeo.save(map);
		reloadMaps();
		updateOrder();
		reloadMap();
	}
	
	protected void updateOrder() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("updateOrder "+map.getViews().size());
		int i=1;
		for(VIEW view : map.getViews())
		{
			view = fGeo.find(fbCore.getClassView(), view);
			logger.trace(i+" "+view.getLayer().toString());
			view.setOrderNo(i);
			fGeo.save(view);
			i++;
		}
	}
	
	public void cancelMap()
	{
		map=null;
		view=null;
	}
	
	public void rmMap()
	{
		logger.info(AbstractLogMessage.rmEntity(map));
		try
		{
			logger.info("Views"+map.getViews().size());
			map = fGeo.find(fbCore.getClassMap(),map);
			logger.info("Views"+map.getViews().size());
			fGeo.rm(map);
			map=null;
			reloadMaps();
		}
		catch (JeeslConstraintViolationException e)
		{
			FacesContextMessage.warn("fmWarn","uieServiceWithLayer");
		}
	}
	
	// LAYER
	public void reorderLayer(ReorderEvent event)
	{
		logger.info(StringUtil.stars());
		logger.info("Reordering Layer, moving old postion "+(event.getFromIndex()+1)+" to new position "+(event.getToIndex()+1));
		int i=1;
		for(VIEW v : map.getViews())
		{
			v.setOrderNo(i);i++;
		}
	}
	
	// VIEW
	public void addView() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbCore.getClassView()));
		view = efView.create(map,null,0,true,true);
		category = categories.get(0);
		changeCategory();
	}
	
	public void selectView() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		category = view.getLayer().getCategory();
		changeCategory();
	}
	
	public void cancelView()
	{
		view=null;
	}
	
	public void saveView() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view.setLayer(fGeo.find(fbCore.getClassLayer(),view.getLayer()));
		view = fGeo.save(view);
		reloadMap();
		updateOrder();
		reloadMap();
		view = fGeo.find(fbCore.getClassView(),view);
		logger.info("VIEW"+view.toString());
	}
	
	public void rmView() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(view));
		fGeo.rm(fbCore.getClassView(),view);
		view=null;
		reloadMap();
		updateOrder();
		reloadMap();
	}
	
	public void changeCategory()
	{
		category = fGeo.load(fbCore.getClassCategory(),category);
		layers = category.getLayer();
		logger.info(AbstractLogMessage.selectOneMenuChange(category));
	}
	
	public void mapMove(AjaxBehaviorEvent ev)
	{
		MapAjaxEvent evt = (MapAjaxEvent) ev;
		logger.trace("Viewport: "+evt.getViewPort().getLat() +"/" +evt.getViewPort().getLon() +" in bounds of " +evt.getViewPort().getTop() +" and " +evt.getViewPort().getBottom());
		efViewPort.update(viewPort,evt.getViewPort());
		logger.info(viewPort.getLon()+"/"+viewPort.getLat());
	}
	
	public void saveViewPort() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
	
	public void reorderMaps() throws JeeslConstraintViolationException, JeeslLockingException{PositionListReorderer.reorder(fGeo,maps);reloadMaps();}
}