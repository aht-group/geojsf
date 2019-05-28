package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.geojsf.event.MapAjaxEvent;
import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
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
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.StringUtil;

public class AbstractMapThematicBean <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
										CATEGORY extends GeoJsfCategory<L,D,LAYER>,
										SERVICE extends GeoJsfService<L,D,LAYER>,
										LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
										MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
										SCALE extends GeoJsfScale<L,D>, 
										VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
										VP extends GeoJsfViewPort,
										DS extends GeoJsfDataSource<L,D,LAYER>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
										SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
										SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
										RULE extends GeoJsfSldRule<L,D,G>>
	extends AbstractGeoJsfBean<L,D,LOC,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapThematicBean.class);
	
	protected List<MAP> maps; public List<MAP> getMaps(){return maps;}
	protected List<CATEGORY> categories; public List<CATEGORY> getCategories(){return categories;}
	protected List<LAYER> layers; public List<LAYER> getLayers(){return layers;}
	
	protected MAP map;
	public MAP getMap() {return map;}
	public void setMap(MAP map) {this.map = map;}
	
	protected VIEW view;
	public VIEW getView(){return view;}
	public void setView(VIEW view){this.view = view;}
	
	protected CATEGORY category;
	public CATEGORY getCategory(){return category;}
	public void setCategory(CATEGORY category){this.category = category;}
	
	protected VP viewPort;
	public VP getViewPort(){return viewPort;}
	public void setViewPort(VP viewPort){this.viewPort = viewPort;}
	
	public AbstractMapThematicBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,DS,VP> fbMeta,
									GeoSldFactoryBuilder<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbCore,fbMeta,fbSld);
	}
	
	protected void postConstructThematic(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		super.postConstructGeojsf(bTranslation.getLangKeys().toArray(new String[0]), fGeo);
		categories = fGeo.allOrderedPositionVisible(fbCore.getClassCategory());
		reloadMaps();
	}
	
	protected void reloadMaps()
	{
		maps = fGeo.allOrderedPosition(fbCore.getClassMap());
	}
	
	public void selectMap() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(map));
		missingLangsMap();
		reloadMap();
		view=null;
	}
	protected void missingLangsMap(){}
	
	protected void reloadMap() throws UtilsConstraintViolationException, UtilsLockingException
	{
		map = fGeo.load(fbCore.getClassMap(),map);
		if(map.getViewPort()==null){addViewPort();}
		else{viewPort=map.getViewPort();}
		logger.info(map.getViewPort().toString());
	}
	
	private void addViewPort() throws UtilsConstraintViolationException, UtilsLockingException
	{
		viewPort = efViewPort.build();
		viewPort = fGeo.save(viewPort);
		map.setViewPort(viewPort);
		map = fGeo.update(map);
	}
	
	public void addMap()
	{
		logger.info(AbstractLogMessage.addEntity(fbCore.getClassMap()));
		map = efMap.create("",langKeys);
		map.setName(efLang.createEmpty(langKeys));
		map.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void saveMap() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(map));
		if(map.getCategory()!=null) {map.setCategory(fGeo.find(fbCore.getClassCategory(), map.getCategory()));}
		map = fGeo.save(map);
		reloadMaps();
		updateOrder();
		reloadMap();
	}
	
	protected void updateOrder() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+map.getViews().size());
		int i=1;
		for(VIEW view : map.getViews())
		{
			view = fGeo.find(fbCore.getClassView(), view);
			logger.trace(i+" "+view.getLayer().toString());
			view.setOrderNo(i);
			fGeo.update(view);
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
		catch (UtilsConstraintViolationException e)
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
	public void addView() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbCore.getClassView()));
		view = efView.create(map,null,0,true,true);
		category = categories.get(0);
		changeCategory();
	}
	
	public void selectView() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		category = view.getLayer().getCategory();
		changeCategory();
	}
	
	public void cancelView()
	{
		view=null;
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException
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
	
	public void rmView() throws UtilsConstraintViolationException, UtilsLockingException
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
	
	public void saveViewPort() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
	
	public void reorderMaps() throws UtilsConstraintViolationException, UtilsLockingException{PositionListReorderer.reorder(fGeo,maps);reloadMaps();}
}