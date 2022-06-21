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
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbCategoryInit <L extends JeeslLang,D extends JeeslDescription,
							G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,G>>
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
					G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		DbCategoryInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, JeeslFacade fAcl)
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
			catch (JeeslNotFoundException e)
			{
				try
				{
					ejb = cCategory.newInstance();
					ejb.setCode(category.getCode());
					ejb =fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (JeeslConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(category.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=(CATEGORY)fSecurity.update(ejb);
			}
			catch (JeeslConstraintViolationException e) {logger.error("",e);}
			catch (JeeslLockingException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}