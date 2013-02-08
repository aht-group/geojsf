package org.geojsf.demo.controller.facade;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.sf.ahtutils.controller.facade.AbstractUtilsFacadeBean;
import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithNr;
import net.sf.ahtutils.model.interfaces.EjbWithValidFrom;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

import org.geojsf.demo.controller.GeoJsfRepository;
import org.geojsf.demo.model.util.GeoLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoFacadeBean extends AbstractUtilsFacadeBean
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoFacadeBean.class);
	
	static final long serialVersionUID=10;
	
	protected final static String migrationError = "This deprecated method is not implemented for JBoss6";
	protected final static String nyiError = "This new method is not yet implemented for JBoss6";
	
    @Inject @GeoJsfRepository
    protected EntityManager em;
	
    protected EjbLangFactory<GeoLang> efLang;
    
	
		
    @PostConstruct
    public void initUtilsFacade()
    {
    	fUtils = new UtilsFacadeBean(em);
    	efLang = EjbLangFactory.createFactory(GeoLang.class);
    }
	
	public <T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException {rmProtected(o);}	
	protected <T extends Object> void rmProtected(T o) throws UtilsIntegrityException {fUtils.rmProtected(o);}
	
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, int nr) throws UtilsNotFoundException
		{return fUtils.fByNr(type, parentName, parent, nr);}
	
	public <T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending) {return fUtils.allOrdered(cl, by, ascending);}
	public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,String by, boolean ascending, String p1Name, I p1) {return fUtils.allOrderedParent(cl, by, ascending, p1Name, p1);}
	
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> cl) {return fUtils.allOrderedPositionVisible(cl);}
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type) {return fUtils.allOrderedPosition(type);}
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending) {return fUtils.allOrderedRecord(type,ascending);}
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,List<ParentPredicate<OR>> lpOr, boolean ascending) {return fUtils.allOrderedForParents(queryClass, lpAnd, lpOr, ascending);}
	
	
	public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException {return fUtils.fFirstValidFrom(type, parentName, id, validFrom);}
	
	public <T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException {return fUtils.oneForParent(cl, p1Name, p1);}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1){return fUtils.allForParent(type, p1Name, p1);}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2)
		{return fUtils.allForParent(type, p1Name, p1, p2Name, p2);}
	
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	   {return fUtils.fForAndOrParents(queryClass, lpAnd, lpOr);}
	
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
		{return fUtils.fForAndOrGrandParents(queryClass, parentClass, parentName, lpAnd, lpOr);}
	
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2)
		{return fUtils.fGrandParents(queryClass, parentClass, parentName, lpOr1, lpOr2);}
	
	@Remove
	public void checkout(){logger.debug("Checkout");}	
}