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
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMapThematicBean<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapThematicBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efCategory;
	protected EjbGeoServiceFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efService;
	protected EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efLayer;
	protected EjbGeoMapFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efMap;
	protected EjbGeoViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efView;
	protected EjbGeoViewPortFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> efViewPort;	
	
	protected GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> fGeo;
	
	protected List<MAP> maps;
	public List<MAP> getMaps(){return maps;}
	
	protected List<CATEGORY> categories;
	public List<CATEGORY> getCategories(){return categories;}
	
	protected List<LAYER> layers;
	public List<LAYER> getLayers(){return layers;}
	
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
	
	private String[] langKeys;
	private Class<MAP> cMap;
	private Class<VIEW> cView;
	private Class<CATEGORY> cCategory;
	private Class<LAYER> cLayer;
	
	public void initSuper(String[] langKeys, final Class<L> cLang, final Class<D> clDescription, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView,final Class<VP> cViewPort)
	{
		this.langKeys=langKeys;
		this.cMap=cMap;
		this.cView=cView;
		this.cCategory=cCategory;
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
	
	protected void reloadMaps()
	{
		maps = fGeo.all(cMap);
	}
	
	public void selectMap() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(map));
		missingLangsMap();
		reloadMap();
		view=null;
	}
	protected void missingLangsMap(){}
	
	protected void reloadMap() throws UtilsContraintViolationException, UtilsLockingException
	{
		map = fGeo.load(cMap,map);
		if(map.getViewPort()==null){addViewPort();}
		else{viewPort=map.getViewPort();}
		logger.info(map.getViewPort().toString());
	}
	
	private void addViewPort() throws UtilsContraintViolationException, UtilsLockingException
	{
		viewPort = efViewPort.build();
		viewPort = fGeo.save(viewPort);
		map.setViewPort(viewPort);
		map = fGeo.update(map);
	}
	
	public void addMap()
	{
		logger.info(AbstractLogMessage.addEntity(cMap));
		map = efMap.create("",langKeys);
		try
		{
			map.setName(efLang.createEmpty(langKeys));
			map.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
	}
	
	public void saveMap() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(map));
		map = fGeo.save(map);
		reloadMaps();
		updateOrder();
		reloadMap();
	}
	
	protected void updateOrder() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+map.getViews().size());
		int i=1;
		for(VIEW view : map.getViews())
		{
			view = fGeo.find(cView, view);
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
			map = fGeo.find(cMap,map);
			logger.info("Views"+map.getViews().size());
			fGeo.rm(map);
			map=null;
			reloadMaps();
		}
		catch (UtilsIntegrityException e)
		{
			FacesContextMessage.warn("fmWarn","uieServiceWithLayer");
		}
	}
	
	// LAYER
	public void reorderLayer(ReorderEvent event)
	{
		logger.info(event.getFromIndex()+" "+event.getToIndex());
		int i=1;
		for(VIEW v : map.getViews())
		{
			v.setOrderNo(i);i++;
		}
	}
	
	// VIEW
	public void addView() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cView));
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
	
	public void saveView() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view.setLayer(fGeo.find(cLayer,view.getLayer()));
		view = fGeo.save(view);
		reloadMap();
		updateOrder();
		reloadMap();
		view = fGeo.find(cView,view);
		logger.info("VIEW"+view.toString());
	}
	
	public void rmView() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(view));
		fGeo.rm(cView,view);
		view=null;
		reloadMap();
		updateOrder();
		reloadMap();
	}
	
	public void changeCategory()
	{
		category = fGeo.load(cCategory,category);
		layers = category.getLayer();
		logger.info(AbstractLogMessage.selectOneMenuChange(category));
	}
	
	
	public void mapMove(MapAjaxEvent evt)
	{
		logger.trace("Viewport: "+evt.getViewPort().getLat() +"/" +evt.getViewPort().getLon() +" in bounds of " +evt.getViewPort().getTop() +" and " +evt.getViewPort().getBottom());	
		efViewPort.update(viewPort,evt.getViewPort());
		logger.info(viewPort.getLon()+"/"+viewPort.getLat());
	}
	
	public void saveViewPort() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(viewPort));
		viewPort = fGeo.save(viewPort);
	}
}