package org.geojsf.util.db.init;

import java.util.List;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.ejb.openlayer.EjbGeoViewLayerFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfViewLayer;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.View;
import org.geojsf.xml.openlayers.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbViewInit <L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
						VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>,
						VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(DbViewInit.class);
	
	private final Class<VIEW> cView;
    private final Class<LAYER> cLayer;
    private final Class<VL> cViewLayer;
    
    private UtilsSecurityFacade fSecurity;
    private GeoJsfFacade fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoViewLayerFactory<L,D,SERVICE,LAYER,VIEW,VL> efViewLayer;
    
    public DbViewInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, final Class<VIEW> cView,final Class<VL> cViewLayer, UtilsSecurityFacade fAcl, GeoJsfFacade fGeo)
	{       
        this.cLayer = cLayer;
        this.cView = cView;
        this.cViewLayer = cViewLayer;
        
        this.fSecurity=fAcl;
        this.fGeo=fGeo;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
		efViewLayer = EjbGeoViewLayerFactory.factory(cViewLayer);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>,
					VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		DbViewInit<L,D,SERVICE,LAYER,VIEW,VL>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, final Class<VIEW> cView,final Class<VL> cViewLayer,UtilsSecurityFacade fAcl,GeoJsfFacade fGeo)
	{
		return new DbViewInit<L,D,SERVICE,LAYER,VIEW,VL>(cL,cD,cLayer,cView,cViewLayer,fAcl,fGeo);
	}
	
	public void iuViews(Views views) throws UtilsConfigurationException
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
					ejb.setX(0d);
					ejb.setY(0d);
					ejb.setZoom(1);
					ejb = (VIEW)fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(view.getLangs()));
				ejb.setZoom(view.getZoom());
				ejb.setX(view.getX());
				ejb.setY(view.getY());
//				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=fSecurity.update(ejb);
				iuViewLayers(ejb,view.getLayer());
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
	
	private void iuViewLayers(VIEW view, List<Layer> layers) throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsIntegrityException
	{
		view = fGeo.load(cView, view);
		logger.trace("Layer: "+view.getLayer().size());
		
		for(VL vl : view.getLayer())
		{
			fGeo.rm(cViewLayer, vl);
		}
		view = fGeo.load(cView, view);
		logger.trace("Layer: "+view.getLayer().size());
		
		int i=1;
		for(Layer layer : layers)
		{
			LAYER l = fSecurity.fByCode(cLayer, layer.getCode());
			VL vl = efViewLayer.create(view, l, i, (layer.isSetVisible() && layer.isVisible()), (layer.isSetShowLegend() && layer.isShowLegend()));
			fSecurity.persist(vl);
			i++;
		}
		view = fSecurity.update(view);
		logger.trace("Layer: "+view.getLayer().size());
	}
}