package org.geojsf.controller.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfFacadeBean <L extends JeeslLang, D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>
								>
				extends JeeslFacadeBean
				implements GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS>
		
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoJsfFacadeBean.class);
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore;
	
	public GeoJsfFacadeBean(EntityManager em, GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore)
	{
		super(em);
		this.fbCore = fbCore;
	}

	@Override public MAP load(MAP map)
	{
		map = em.find(fbCore.getClassMap(), map.getId());
		map.getViews().size();
		if(map.getViewPort()!=null){map.getViewPort().getId();}
		return map;
	}
	
	@Override public CATEGORY load(CATEGORY category)
	{
		category = em.find(fbCore.getClassCategory(), category.getId());
		category.getLayer().size();
		return category;
	}

	@Override public SERVICE load(SERVICE service)
	{
		service = em.find(fbCore.getClassService(), service.getId());
		service.getLayer().size();
		return service;
	}
	
	@Override public LAYER load(LAYER layer)
	{
		layer = em.find(fbCore.getClassLayer(), layer.getId());
		if(layer.getViewPort()!=null){layer.getViewPort().getId();}
		return layer;
	}

	@Override public void rm(Class<VIEW> cView, VIEW view)
	{
		logger.info("rm "+cView.getName()+" "+view.toString());
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
	
	@Override
	public List<VIEW> fGeoViews(LAYER layer)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<VIEW> cQ = cB.createQuery(fbCore.getClassView());
		Root<VIEW> root = cQ.from(fbCore.getClassView());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<LAYER> pLayer = root.get(GeoJsfView.Attributes.layer.toString());
		predicates.add(cB.equal(pLayer,layer));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
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