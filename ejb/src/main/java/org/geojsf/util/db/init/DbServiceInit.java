package org.geojsf.util.db.init;

import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Service;
import org.jeesl.controller.io.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceInit <L extends JeeslLang,D extends JeeslDescription,
							SERVICE extends GeoJsfService<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(DbServiceInit.class);
	
    private final Class<SERVICE> cService;
    
    private JeeslFacade fSecurity;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbServiceInit(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, JeeslFacade fAcl)
	{       
        this.cService = cService;
        
        this.fSecurity=fAcl;
        
        ejbLangFactory = EjbLangFactory.instance(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.factory(cD);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					SERVICE extends GeoJsfService<L,D,?>>
		DbServiceInit<L,D,SERVICE>
		factory(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, JeeslFacade fAcl)
	{
		return new DbServiceInit<L,D,SERVICE>(cL,cD,cService,fAcl);
	}
	
	public void iuServices(Repository repository) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Repository.class.getSimpleName()+" with "+repository.getService().size()+" "+Service.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<SERVICE> ejbUpdater = JeeslDbCodeEjbUpdater.instance(cService);
		ejbUpdater.dbEjbs(fSecurity.all(cService));

		for(Service service : repository.getService())
		{
			ejbUpdater.handled(service.getCode());
			
			SERVICE ejb;
			try
			{
				ejb = fSecurity.fByCode(cService,service.getCode());
				ejbLangFactory.rmLang(fSecurity,ejb);
				ejbDescriptionFactory.rmDescription(fSecurity,ejb);
			}
			catch (JeeslNotFoundException e)
			{
				try
				{
					ejb = cService.newInstance();
					ejb.setCode(service.getCode());
					ejb.setWms(service.getWms());
					ejb.setWcs(service.getWcs());
					ejb =fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (JeeslConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(service.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(service.getDescriptions()));
				ejb.setWms(service.getWms());
				ejb.setWcs(service.getWcs());
				ejb=(SERVICE)fSecurity.update(ejb);
			}
			catch (JeeslConstraintViolationException e) {logger.error("",e);}
			catch (JeeslLockingException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}