package org.geojsf.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.util.with.EjbWithSld;
import org.geojsf.interfaces.util.with.EjbWithSldRules;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFacadeBean <L extends JeeslLang, D extends JeeslDescription,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
								TYPE extends GeoJsfSldType<L,D,TYPE,?>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,TYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,?>
								>
				extends JeeslFacadeBean
				implements GeoSldFacade<L,D,SLDTEMPLATE,SLD,TYPE,RULE>
		
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(GeoSldFacadeBean.class);
	
	private final GeoSldFactoryBuilder<L,D,?,SLDTEMPLATE,SLD,TYPE,RULE> fbSld;

	
	public GeoSldFacadeBean(EntityManager em, GeoSldFactoryBuilder<L,D,?,SLDTEMPLATE,SLD,TYPE,RULE> fbSld)
	{
		super(em);
		this.fbSld=fbSld;
	}
	
	@Override public SLD load(SLD sld)
	{
		sld = em.find(fbSld.getClassSld(), sld.getId());
		for(RULE r : sld.getRules())
		{
			if(r.getGraphic()!=null){r.getGraphic().getId();}
		}
		sld.getRules().size();
		return sld;
	}
	
	@Override public RULE load(Class<RULE> cRule, RULE rule)
	{
		rule = em.find(cRule, rule.getId());
		rule.getGraphic().getId();
		return rule;
	}
	
	@Override public List<SLD> fLibrarySlds()
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<SLD> criteriaQuery = cB.createQuery(fbSld.getClassSld());
	    Root<SLD> fromType = criteriaQuery.from(fbSld.getClassSld());
	    
	    Path<Boolean> pLibrary = fromType.get(GeoJsfSld.Attributes.library.toString());
	    
	    CriteriaQuery<SLD> select = criteriaQuery.select(fromType);
	    select.where(cB.equal(pLibrary,true));
		return em.createQuery(select).getResultList();
	}
	
	@Override public <W extends EjbWithSld<SLD>> SLD fSld(Class<W> cW, W entity) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SLD> cQ = cB.createQuery(fbSld.getClassSld());
		Root<W> w = cQ.from(cW);
		
		Path<SLD> pSld = w.get(EjbWithSld.Attributes.sld.toString());
		cQ.where(cB.equal(w,entity));
		cQ.select(pSld);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No Graphic found for status.id"+entity.toString());}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Multiple Results for status.id"+entity.toString());}
	}
	
	@Override public <W extends EjbWithSldRules<RULE>> RULE save(Class<W> cW, W entity, RULE rule) throws JeeslLockingException, JeeslConstraintViolationException
	{
		entity = this.find(cW, entity);
		rule = this.saveProtected(rule);
		if(!entity.getRules().contains(rule))
		{
			entity.getRules().add(rule);
			this.saveProtected(entity);
		}
		return rule;
	}
	
	@Override public <W extends EjbWithSldRules<RULE>> void delete(Class<W> cW, W entity, RULE rule) throws JeeslConstraintViolationException, JeeslLockingException
	{
		entity = this.find(cW, entity);
		if(entity.getRules().contains(rule))
		{
			entity.getRules().remove(rule);
			this.saveProtected(entity);
		}
		this.rmProtected(rule);		
	}
}