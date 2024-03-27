package org.geojsf.util.db.init;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.model.xml.geojsf.Category;
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

public class DbCategoryInit <L extends JeeslLang,D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,?>
							>
{
	final static Logger logger = LoggerFactory.getLogger(DbCategoryInit.class);
	
    private final Class<CATEGORY> cCategory;
    
    private JeeslFacade fSecurity;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbCategoryInit(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, JeeslFacade fAcl)
	{       
        this.cCategory = cCategory;
        
        this.fSecurity=fAcl;
        
        ejbLangFactory = EjbLangFactory.instance(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.factory(cD);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,?>>
		DbCategoryInit<L,D,CATEGORY>
		factory(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, JeeslFacade fAcl)
	{
		return new DbCategoryInit<L,D,CATEGORY>(cL,cD,cCategory,fAcl);
	}
	
	public void iuServices(Repository categories) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Repository.class.getSimpleName()+" with "+categories.getService().size()+" "+Service.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<CATEGORY> ejbUpdater = JeeslDbCodeEjbUpdater.instance(cCategory);
		ejbUpdater.dbEjbs(fSecurity.all(cCategory));

		for(Category category : categories.getCategory())
		{
			ejbUpdater.handled(category.getCode());
			
			CATEGORY ejb;
			try
			{
				ejb = fSecurity.fByCode(cCategory,category.getCode());
				ejbLangFactory.rmLang(fSecurity,ejb);
				ejbDescriptionFactory.rmDescription(fSecurity,ejb);
			}
			catch (JeeslNotFoundException e)
			{
				try
				{
					ejb = cCategory.newInstance();
					ejb.setCode(category.getCode());
					ejb =fSecurity.save(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (JeeslConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (JeeslLockingException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(category.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=(CATEGORY)fSecurity.save(ejb);
			}
			catch (JeeslConstraintViolationException e) {logger.error("",e);}
			catch (JeeslLockingException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}