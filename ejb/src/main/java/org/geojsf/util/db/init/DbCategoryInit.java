package org.geojsf.util.db.init;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Service;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DbCategoryInit <L extends UtilsLang,D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,G>>
{
	final static Logger logger = LoggerFactory.getLogger(DbCategoryInit.class);
	
    private final Class<CATEGORY> cCategory;
    
    private UtilsFacade fSecurity;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbCategoryInit(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, UtilsFacade fAcl)
	{       
        this.cCategory = cCategory;
        
        this.fSecurity=fAcl;
        
        ejbLangFactory = EjbLangFactory.factory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.factory(cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		DbCategoryInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, UtilsFacade fAcl)
	{
		return new DbCategoryInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cL,cD,cCategory,fAcl);
	}
	
	public void iuServices(Repository categories) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Repository.class.getSimpleName()+" with "+categories.getService().size()+" "+Service.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<CATEGORY> ejbUpdater = JeeslDbCodeEjbUpdater.createFactory(cCategory);
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
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cCategory.newInstance();
					ejb.setCode(category.getCode());
					ejb =fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(category.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=(CATEGORY)fSecurity.update(ejb);
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}