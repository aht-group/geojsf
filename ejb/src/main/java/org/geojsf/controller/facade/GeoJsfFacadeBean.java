package org.geojsf.controller.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class GeoJsfFacadeBean <L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	implements GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfFacadeBean.class);
	
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
	
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, Set<Long> ids)
	{
		return find(cl,new ArrayList<Long>(ids));
	}
	
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, List<Long> ids)
	{
		if(ids==null || ids.size()==0){return new ArrayList<T>();}
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<T> cQ = cB.createQuery(cl);
        Root<T> root = cQ.from(cl);
        Path<Long> path = root.get("id");
        cQ.where(cB.isTrue(path.in(ids)));

		TypedQuery<T> q = em.createQuery(cQ); 
		return q.getResultList();
	}
	
	@Override public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException
	{
		T o = em.find(type,id);
		if(o==null){throw new UtilsNotFoundException("No entity "+type+" with id="+id);}
		return o;
	}
	@Override public <T extends EjbWithId> T find(Class<T> type, T t) {return em.find(type,t.getId());}

	@Override
	public MAP load(Class<MAP> cView, MAP map)
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
	
	@Override public SLD load(Class<SLD> cSld, SLD sld)
	{
		sld = em.find(cSld, sld.getId());
		sld.getRules().size();
		return sld;
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
