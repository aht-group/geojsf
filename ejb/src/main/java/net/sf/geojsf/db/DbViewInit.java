package net.sf.geojsf.db;

import java.util.List;

import net.sf.ahtutils.controller.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.xml.openlayers.Layer;
import net.sf.geojsf.xml.openlayers.View;
import net.sf.geojsf.xml.openlayers.Views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbViewInit <L extends UtilsLang,
 							D extends UtilsDescription,
 							LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
 							VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
 							SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(DbViewInit.class);
	
	private final Class<VIEW> cView;
    private final Class<LAYER> cLayer;
    
    private UtilsSecurityFacade fSecurity;
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbViewInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, final Class<VIEW> cView,UtilsSecurityFacade fAcl)
	{       
        this.cLayer = cLayer;
        this.cView = cView;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
	   			   VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
	   			   SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		DbViewInit<L,D,LAYER,VIEW,SERVICE>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, final Class<VIEW> cView,UtilsSecurityFacade fAcl)
	{
		return new DbViewInit<L,D,LAYER,VIEW,SERVICE>(cL,cD,cLayer,cView,fAcl);
	}
	
	public void iuLayer(Views views) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Views.class.getSimpleName()+" with "+views.getView()+" "+View.class.getSimpleName());
		
		AhtDbEjbUpdater<VIEW> ejbUpdater = AhtDbEjbUpdater.createFactory(cView);
		ejbUpdater.dbEjbs(fSecurity.all(cView));

		for(View view : views.getView())
		{
			ejbUpdater.actualAdd(view.getCode());
			
			VIEW ejb;
			try
			{
				ejb = fSecurity.fByCode(cView,view.getCode());
				ejbLangFactory.rmLang(fSecurity,ejb);
				ejbDescriptionFactory.rmDescription(fSecurity,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cView.newInstance();
					ejb.setCode(view.getCode());
					ejb = (VIEW)fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(view.getLangs()));
//				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=fSecurity.update(ejb);
				iuLayers(ejb,view.getLayer());
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	private void iuLayers(VIEW view, List<Layer> layers) throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		view.getLayer().clear();
		view = fSecurity.update(view);
		logger.trace("Layer: "+view.getLayer().size());
		for(Layer layer : layers)
		{
			view.getLayer().add(fSecurity.fByCode(cLayer, layer.getCode()));
		}
		view = fSecurity.update(view);
		logger.trace("Layer: "+view.getLayer().size());
	}
}