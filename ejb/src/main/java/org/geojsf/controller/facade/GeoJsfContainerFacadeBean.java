package org.geojsf.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.geojsf.interfaces.facade.GeoJsfContainerFacade;
import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.geojsf.interfaces.model.with.container.EjbWithPolygonContainer;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.MultiPolygon;

public class GeoJsfContainerFacadeBean <P extends EjbWithGeometry<MultiPolygon>>
				extends JeeslFacadeBean
				implements GeoJsfContainerFacade<P>
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfContainerFacadeBean.class);
	
	private final Class<P> cP;
	
	public GeoJsfContainerFacadeBean(EntityManager em, final Class<P> cP)
	{
		super(em);
		this.cP=cP;
	}

	@Override public <W extends EjbWithPolygonContainer<P>> P fPolygon(Class<W> c, W with) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<P> cQ = cB.createQuery(cP);
		Root<W> w = cQ.from(c);
		
		Path<P> pPolygon = w.get("polygon");
		Path<Long> pId = w.get("id");
		
		cQ.where(cB.equal(pId,with.getId()));
		cQ.select(pPolygon);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No Graphic found for status.id"+with.getId());}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Multiple Results for status.id"+with.getId());}
	}
}