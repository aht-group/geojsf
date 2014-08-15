package org.geojsf.util.db.init;

import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceInit <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
{
	final static Logger logger = LoggerFactory.getLogger(DbServiceInit.class);
	
    private final Class<SERVICE> cService;
    
    private UtilsFacade fSecurity;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbServiceInit(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, UtilsFacade fAcl)
	{       
        this.cService = cService;
        
        this.fSecurity=fAcl;
        
        ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		DbServiceInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>
		factory(final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService, UtilsFacade fAcl)
	{
		return new DbServiceInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>(cL,cD,cService,fAcl);
	}
	
	public void iuServices(Repository repository) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Repository.class.getSimpleName()+" with "+repository.getService().size()+" "+Service.class.getSimpleName());
		
		AhtDbEjbUpdater<SERVICE> ejbUpdater = AhtDbEjbUpdater.createFactory(cService);
		ejbUpdater.dbEjbs(fSecurity.all(cService));

		for(Service service : repository.getService())
		{
			ejbUpdater.actualAdd(service.getCode());
			
			SERVICE ejb;
			try
			{
				ejb = fSecurity.fByCode(cService,service.getCode());
				ejbLangFactory.rmLang(fSecurity,ejb);
				ejbDescriptionFactory.rmDescription(fSecurity,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cService.newInstance();
					ejb.setCode(service.getCode());
					ejb.setUrl(service.getUrl());
					ejb =fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(service.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(service.getDescriptions()));
				ejb=(SERVICE)fSecurity.update(ejb);
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}