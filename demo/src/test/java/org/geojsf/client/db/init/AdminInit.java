package org.geojsf.client.db.init;

import java.io.FileNotFoundException;

import javax.naming.NamingException;

import net.sf.ahtutils.controller.UtilsJbossFacadeLookup;
import net.sf.ahtutils.db.ejb.security.AbstractSecurityInit;
import net.sf.ahtutils.db.ejb.security.SecurityInitRoles;
import net.sf.ahtutils.db.ejb.security.SecurityInitUsecases;
import net.sf.ahtutils.db.ejb.security.SecurityInitViews;
import net.sf.ahtutils.db.xml.AhtDbXmlInit;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.db.xml.AhtXmlInitIdMapper;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.demo.controller.interfaces.facade.GeoUtilsFacade;
import net.sf.geojsf.demo.model.user.GeoUser;
import net.sf.geojsf.demo.model.util.GeoDescription;
import net.sf.geojsf.demo.model.util.GeoLang;
import net.sf.geojsf.demo.model.util.security.SecurityAction;
import net.sf.geojsf.demo.model.util.security.SecurityCategory;
import net.sf.geojsf.demo.model.util.security.SecurityRole;
import net.sf.geojsf.demo.model.util.security.SecurityUsecase;
import net.sf.geojsf.demo.model.util.security.SecurityView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminInit extends AbstractErpInit<GeoLang> implements AhtDbXmlInit
{
	public static enum Code {responsibilityRoles,responsibilities}
	final static Logger logger = LoggerFactory.getLogger(AdminInit.class);
	
	private GeoUtilsFacade fUtils;
	
	private SecurityInitRoles<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser> securityInitRoles;
	private SecurityInitViews<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser> securityInitViews;
	private SecurityInitUsecases<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser> securityInitUsecases;
	
	public AdminInit(Db dbSeed,UtilsJbossFacadeLookup fl,AhtXmlInitIdMapper idMapper, AhtStatusDbInit asdi) throws NamingException
	{
		super(GeoLang.class,dbSeed,fl,idMapper,asdi);
		fUtils = fl.lookup(GeoUtilsFacade.class);
		
		securityInitRoles = AbstractSecurityInit.factoryRoles(GeoLang.class,GeoDescription.class,SecurityCategory.class,SecurityRole.class,SecurityView.class,SecurityUsecase.class,SecurityAction.class,GeoUser.class,fUtils);
		securityInitViews = AbstractSecurityInit.factoryViews(GeoLang.class,GeoDescription.class,SecurityCategory.class,SecurityRole.class,SecurityView.class,SecurityUsecase.class,SecurityAction.class,GeoUser.class,fUtils);
		securityInitUsecases = AbstractSecurityInit.factoryUsecases(GeoLang.class,GeoDescription.class,SecurityCategory.class,SecurityRole.class,SecurityView.class,SecurityUsecase.class,SecurityAction.class,GeoUser.class,fUtils);
	}
	
	@Override
	protected void initStatics() throws FileNotFoundException, UtilsConfigurationException
	{
		logger.debug("InitStatics");

		securityInitViews.iuViews(JaxbUtil.loadJAXB(getExtractName(UtilsSecurityView.extractId), Access.class));
//		securityInitUsecases.iuUsecases(JaxbUtil.loadJAXB(getExtractName(UtilsSecurityUsecase.extractId), Access.class));
//		securityInitRoles.iuRoles(JaxbUtil.loadJAXB(getExtractName(UtilsSecurityRole.extractId), Access.class));
					
	}
	
	@Override
	protected void initRequired() throws FileNotFoundException, UtilsConfigurationException
	{

	}
}