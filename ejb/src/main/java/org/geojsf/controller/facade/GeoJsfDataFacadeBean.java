package org.geojsf.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.geojsf.interfaces.facade.GeoJsfDataFacade;
import org.geojsf.interfaces.model.geometry.EjbWithMultiPolygon;
import org.geojsf.interfaces.model.geometry.GeoJsfMultiPolygon;
import org.geojsf.interfaces.model.with.container.EjbWithPolygonContainer;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDataFacadeBean <MP extends GeoJsfMultiPolygon> extends JeeslFacadeBean implements GeoJsfDataFacade<MP>
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDataFacadeBean.class);
	
	private final Class<MP> cMP;
	
	public GeoJsfDataFacadeBean(EntityManager em, final Class<MP> cMP)
	{
		super(em);
		this.cMP=cMP;
	}

//	@Override public <W extends EjbWithPolygonContainer<P>> P fPolygon(Class<W> c, W with) throws JeeslNotFoundException
//	{
//		CriteriaBuilder cB = em.getCriteriaBuilder();
//		CriteriaQuery<P> cQ = cB.createQuery(cP);
//		Root<W> w = cQ.from(c);
//		
//		Path<P> pPolygon = w.get("polygon");
//		Path<Long> pId = w.get("id");
//		
//		cQ.where(cB.equal(pId,with.getId()));
//		cQ.select(pPolygon);
//		
//		try	{return em.createQuery(cQ).getSingleResult();}
//		catch (NoResultException ex){throw new JeeslNotFoundException("No Graphic found for status.id"+with.getId());}
//		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Multiple Results for status.id"+with.getId());}
//	}

//	@Override
//	public void x() {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public <OWNER extends EjbWithMultiPolygon<MP>> MP fGeoMultiPolygon(Class<OWNER> cOwner, OWNER owner) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<MP> cQ = cB.createQuery(cMP);
		Root<OWNER> root = cQ.from(cOwner);
		
		Path<MP> pPolygon = root.get("polygon");
		Path<Long> pId = root.get("id");
		
		cQ.where(cB.equal(pId,owner.getId()));
		cQ.select(pPolygon);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No Graphic found for status.id"+owner.getId());}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Multiple Results for status.id"+owner.getId());}
	}
}