package net.sf.geojsf.db;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.xml.openlayers.Repository;
import net.sf.geojsf.xml.openlayers.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceInit <L extends UtilsLang,
 							D extends UtilsDescription,
 							LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
 							VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
 							SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(DbServiceInit.class);
	
    private final Class<SERVICE> cService;
    
    private UtilsSecurityFacade fSecurity;
    
    public DbServiceInit(final Class<SERVICE> cService, UtilsSecurityFacade fAcl)
	{       
        this.cService = cService;
        
        this.fSecurity=fAcl;
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
	   			   VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
	   			   SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		DbServiceInit<L,D,LAYER,VIEW,SERVICE>
		factory(final Class<SERVICE> cService, UtilsSecurityFacade fAcl)
	{
		return new DbServiceInit<L,D,LAYER,VIEW,SERVICE>(cService,fAcl);
	}
	
	public void iuService(Repository repository) throws UtilsConfigurationException
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
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cService.newInstance();
					ejb.setCode(service.getCode());
					ejb =fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}