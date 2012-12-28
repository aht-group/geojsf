package net.sf.geojsf.demo.controller.facade;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import net.sf.ahtutils.controller.facade.UtilsSecurityFacadeBean;
import net.sf.ahtutils.controller.facade.UtilsTrackerFacadeBean;
import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.controller.interfaces.UtilsTrackerFacade;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;
import net.sf.geojsf.demo.controller.interfaces.facade.GeoUtilsFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Remote(GeoUtilsFacade.class)
public class GeoUtilsFacadeBean extends AbstractGeoFacadeBean implements GeoUtilsFacade,Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoUtilsFacadeBean.class);
	
	private UtilsTrackerFacade fTracker;
	private UtilsSecurityFacade fSecurity;
	
    @PostConstruct
    public void initTrackerFacade()
    {
    	fTracker = new UtilsTrackerFacadeBean(em);
    	fSecurity = new UtilsSecurityFacadeBean(em);
    }
    
	static final long serialVersionUID=1;
	
	public <T extends Object> T persistAhtUtilsStatus(T o) throws UtilsContraintViolationException
	{
		try {return persist(o);}
		catch (UtilsContraintViolationException e) {throw new UtilsContraintViolationException(e.getMessage());}
	}
	
	public <T extends Object> T updateAhtUtilsStatus(T o) throws UtilsContraintViolationException
	{
		try {return update(o);}
		catch (UtilsContraintViolationException e) {throw new UtilsContraintViolationException(e.getMessage());}
		catch (UtilsLockingException e) {throw new UtilsContraintViolationException(e.getMessage());}
	}
	
	public <T extends Object> T fAhtUtilsEntity(Class<T> type, long id) throws UtilsNotFoundException
	{
		T t = em.find(type,id);
		if(t==null){throw new UtilsNotFoundException("No Entity "+type.getName()+" with id="+id);}
		return t;
	}
	
	public <T extends EjbRemoveable> void rmAhtUtilsEntity(T o) throws UtilsContraintViolationException
	{
		try {rm((EjbRemoveable)o);}
		catch (UtilsIntegrityException e) {throw new UtilsContraintViolationException(e.getMessage());}
	}
	
	public <T extends UtilsStatus<L,D>,L extends UtilsLang,D extends UtilsDescription> T fAhtUtilsStatusByCode(Class<T> type, String code) throws UtilsNotFoundException
	{
		try {return fByCode(type,code);}
		catch (UtilsNotFoundException e) {throw new UtilsNotFoundException(e.getMessage());}
	}
	public <T extends UtilsStatus<L,D>,L extends UtilsLang,D extends UtilsDescription> List<T> allAhtUtilsStatus(Class<T> type)
	{
		return all(type);
	}

	@Override
	public <TR extends UtilsTracker<TR, TL, T, S, L,D>, TL extends UtilsTrackerLog<TR, TL, T, S, L,D>, T extends UtilsStatus<L,D>, S extends UtilsStatus<L,D>, L extends UtilsLang,D extends UtilsDescription>
		List<TR> allTrackerForType(Class<TR> clTracker, Class<T> clType, T type)
	{return fTracker.allTrackerForType(clTracker, clType, type);}

	@Override
	public <TR extends UtilsTracker<TR, TL, T, S, L,D>, TL extends UtilsTrackerLog<TR, TL, T, S, L,D>, T extends UtilsStatus<L,D>, S extends UtilsStatus<L,D>, L extends UtilsLang,D extends UtilsDescription>
		TR fTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{return fTracker.fTracker(clTracker, clType, type, refId);}

	@Override
	public <TR extends UtilsMailTracker<T, L, U,D>, T extends UtilsStatus<L,D>, L extends UtilsLang, U extends EjbWithId,D extends UtilsDescription>
		List<TR> fMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{return fTracker.fMailTracker(clTracker, clType, type, refId);}

	@Override
	public <TR extends UtilsMailTracker<T, L, U,D>, T extends UtilsStatus<L,D>, L extends UtilsLang, U extends EjbWithId,D extends UtilsDescription>
		TR fLastMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{return fTracker.fLastMailTracker(clTracker, clType, type, refId);}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<V>	allViewsForUser(Class<USER> clUser, USER user)
	{return fSecurity.allViewsForUser(clUser, user);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		List<A> allActionsForUser(Class<USER> clUser, USER user)
	{return fSecurity.allActionsForUser(clUser, user);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		List<R> allRolesForUser(Class<USER> clUser, USER user)
	{return fSecurity.allRolesForUser(clUser, user);}
	
	@Override
	public <WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,USER>, L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String code) throws UtilsNotFoundException
	{return fSecurity.allForCategory(clWc, clC, code);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, S extends UtilsStaff<L,D,C,R,V,U,A,P,E,USER>, P extends EjbWithId, E extends EjbWithId,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S>	fStaff(Class<S> clStaff, P pool)
	{return fSecurity.fStaff(clStaff, pool);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		R load(Class<R> cRole, R role)
	{return fSecurity.load(cRole, role);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		V load(Class<V> cView, V view)
	{return fSecurity.load(cView, view);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		void grantRole(Class<USER> clUser, Class<R> clRole,USER user, R role, boolean grant)
	{fSecurity.grantRole(clUser, clRole, user, role, grant);}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{return fSecurity.hasRole(clUser, clRole, user, role);}



}