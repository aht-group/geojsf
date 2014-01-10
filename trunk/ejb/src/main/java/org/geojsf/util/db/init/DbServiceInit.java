package org.geojsf.util.db.init;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewLayer;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceInit <L extends UtilsLang,
							D extends UtilsDescription,
							SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
							LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
							VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
							VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
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
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
					VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					LT extends UtilsStatus<L,D>>
		DbServiceInit<L,D,SERVICE,LAYER,VIEW,VL>
		factory(final Class<SERVICE> cService, UtilsSecurityFacade fAcl)
	{
		return new DbServiceInit<L,D,SERVICE,LAYER,VIEW,VL>(cService,fAcl);
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
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}