package org.geojsf.controller.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfFacadeBean <L extends JeeslLang, D extends JeeslDescription,
								G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
								F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>
								>
				extends JeeslFacadeBean
				implements GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS>
		
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(GeoJsfFacadeBean.class);
	
	public GeoJsfFacadeBean(EntityManager em, GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore)
	{
		super(em);
	}

	@Override public MAP load(Class<MAP> cView, MAP map)
	{
		map = em.find(cView, map.getId());
		map.getViews().size();
		if(map.getViewPort()!=null){map.getViewPort().getId();}
		return map;
	}
	
	@Override public CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category)
	{
		category = em.find(cCategory, category.getId());
		category.getLayer().size();
		return category;
	}

	@Override public SERVICE load(Class<SERVICE> cService, SERVICE service)
	{
		service = em.find(cService, service.getId());
		service.getLayer().size();
		return service;
	}
	
	@Override public LAYER load(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		if(layer.getViewPort()!=null){layer.getViewPort().getId();}
		return layer;
	}
	
	
	
	@Override public DS load(Class<DS> cDs, DS ds)
	{
		ds = em.find(cDs, ds.getId());
		ds.getLayers().size();
		return ds;
	}

	@Override public void rm(Class<VIEW> cView, VIEW view)
	{
		view = em.find(cView, view.getId());
		view.getMap().getViews().remove(view);
		em.remove(view);
	}

	@Override
	public void rm(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		layer.getCategory().getLayer().remove(layer);
		layer.getService().getLayer().remove(layer);
		em.remove(layer);
	}
	
	public List<DS> fDataSources(Class<MAP> cMap, Class<DS> cDs, MAP map)
	{
		map = this.find(cMap,map);
		List<DS> tmp = new ArrayList<DS>();
		Set<LAYER> layers = new HashSet<LAYER>();
		
		for(VIEW view : map.getViews())
		{
			layers.add(view.getLayer());
			for(DS ds : view.getLayer().getSources())
			{
				if(!tmp.contains(ds))
				{
					tmp.add(ds);
				}
			}
		}
		
		List<DS> result = new ArrayList<DS>();
		for(DS ds : tmp)
		{
			logger.info("Adding ds "+ds.toString());
			try
			{
				ds = this.find(cDs,ds);
				DS dsAdd = cDs.newInstance();
				dsAdd.setId(ds.getId());
				dsAdd.setDescription(ds.getDescription());
				dsAdd.setName(ds.getName());
				for(LAYER l : ds.getLayers())
				{
					logger.info("Testing layer "+l.toString());
					if(layers.contains(l))
					{
						logger.info("\tAdding layer "+l.toString());
						dsAdd.getLayers().add(l);
					}
				}
				result.add(dsAdd);
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
		}
		return result;
	}
	
	
}