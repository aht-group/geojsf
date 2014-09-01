package org.geojsf.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;

public class GeoJsfFacadeBean implements GeoJsfFacade
{	
	private EntityManager em;
	
	public GeoJsfFacadeBean(EntityManager em)
	{
		this.em=em;
	}
	
	@Override public <T extends Object> List<T> all(Class<T> type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		return typedQuery.getResultList();
	}
	
	@Override public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException
	{
		T o = em.find(type,id);
		if(o==null){throw new UtilsNotFoundException("No entity "+type+" with id="+id);}
		return o;
	}
	@Override public <T extends EjbWithId> T find(Class<T> type, T t) {return em.find(type,t.getId());}

	@Override
	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		MAP load(Class<MAP> cView, MAP map)
	{
		map = em.find(cView, map.getId());
		map.getViews().size();
		if(map.getViewPort()!=null){map.getViewPort().getId();}
		return map;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, CATEGORY extends GeoJsfCategory<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, SERVICE extends GeoJsfService<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, LAYER extends GeoJsfLayer<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, MAP extends GeoJsfMap<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, VIEW extends GeoJsfView<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, VP extends GeoJsfViewPort<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>>
		CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category)
	{
		category = em.find(cCategory, category.getId());
		category.getLayer().size();
		return category;
	}

	@Override
	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		SERVICE load(Class<SERVICE> cService, SERVICE service)
	{
		service = em.find(cService, service.getId());
		service.getLayer().size();
		return service;
	}
	
	@Override
	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		LAYER load(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		if(layer.getViewPort()!=null){layer.getViewPort().getId();}
		return layer;
	}

	@Override
	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		void rm(Class<VIEW> cView, VIEW view)
	{
		view = em.find(cView, view.getId());
		view.getMap().getViews().remove(view);
		em.remove(view);
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, CATEGORY extends GeoJsfCategory<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, SERVICE extends GeoJsfService<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, LAYER extends GeoJsfLayer<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, MAP extends GeoJsfMap<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, VIEW extends GeoJsfView<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>, VP extends GeoJsfViewPort<L, D, CATEGORY, SERVICE, LAYER, MAP, VIEW, VP>>
		void rm(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		layer.getCategory().getLayer().remove(layer);
		layer.getService().getLayer().remove(layer);
		em.remove(layer);
	}
}
