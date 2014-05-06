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

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbLayerInit <L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
						VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>,
						VL extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
    private final Class<LAYER> cLayer;
    private final Class<SERVICE> cService;
    
    private UtilsFacade fUtils;
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoLayerFactory<L,D,SERVICE,LAYER,VIEW,VL> ejbLayerFactory;
    
    public DbLayerInit(final Class<L> cL, final Class<D> cD,final Class<SERVICE> cService, final Class<LAYER> cLayer,UtilsFacade fUtils)
	{       
        this.cLayer = cLayer;
        this.cService = cService;
        
        this.fUtils=fUtils;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
		ejbLayerFactory = EjbGeoLayerFactory.factory(cL, cLayer);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>,
					VL extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>>
		DbLayerInit<L,D,SERVICE,LAYER,VIEW,VL>
		factory(final Class<L> cL, final Class<D> cD,final Class<SERVICE> cService, final Class<LAYER> cLayer,UtilsFacade fUtils)
	{
		return new DbLayerInit<L,D,SERVICE,LAYER,VIEW,VL>(cL,cD,cService,cLayer,fUtils);
	}

	public void iuLayers(Layers layers, String[] langKeys) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Layers.class.getSimpleName()+" with "+layers.getLayer().size()+" "+Layer.class.getSimpleName());
		
		AhtDbEjbUpdater<LAYER> updateLayer = AhtDbEjbUpdater.createFactory(cLayer);
		
		updateLayer.dbEjbs(fUtils.all(cLayer));

		for(Layer layer : layers.getLayer())
		{
			updateLayer.actualAdd(layer.getCode());
			
			SERVICE service;			
			try
			{
				service = fUtils.fByCode(cService, layer.getService().getCode());
			}
			catch (UtilsNotFoundException e1) {throw new UtilsConfigurationException(e1.getMessage());}
			
			
			LAYER ejb;
			try
			{
				ejb = fUtils.fByCode(cLayer,layer.getCode());
				ejbLangFactory.rmLang(fUtils,ejb);
				ejbDescriptionFactory.rmDescription(fUtils,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = ejbLayerFactory.build(layer.getCode(), service, langKeys);					
					ejb = (LAYER)fUtils.persist(ejb);
				}

				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsIntegrityException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(layer.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(layer.getDescriptions()));
				
				ejb.setTemporalLayer(layer.isTemporal());
				ejb.setService(fUtils.fByCode(cService, layer.getService().getCode()));
				
				ejb=(LAYER)fUtils.update(ejb);
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}