package org.geojsf.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFacadeBean <L extends JeeslLang, D extends JeeslDescription,
								G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
								F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
								SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>
								>
				extends JeeslFacadeBean
				implements GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(GeoSldFacadeBean.class);
	
	private final Class<SLD> cSld;
	
	public GeoSldFacadeBean(EntityManager em, GeoSldFactoryBuilder<L,D,?,?,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(em);
		this.cSld=fbSld.getClassSld();
	}
	
	@Override public SLD load(SLD sld)
	{
		sld = em.find(cSld, sld.getId());
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
	    CriteriaQuery<SLD> criteriaQuery = cB.createQuery(cSld);
	    Root<SLD> fromType = criteriaQuery.from(cSld);
	    
	    Path<Boolean> pLibrary = fromType.get(GeoJsfSld.Attributes.library.toString());
	    
	    CriteriaQuery<SLD> select = criteriaQuery.select(fromType);
	    select.where(cB.equal(pLibrary,true));
		return em.createQuery(select).getResultList();
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