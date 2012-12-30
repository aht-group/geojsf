package net.sf.geojsf.db.init;

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
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfLayerType;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Layers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbLayerInit <L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>,
						VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,
						LT extends UtilsStatus<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
    private final Class<LAYER> cLayer;
    private final Class<SERVICE> cService;
    private final Class<LT> cLayerType;
    
    private UtilsSecurityFacade fSecurity;
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoLayerFactory<L,D,SERVICE,LAYER,VIEW,VL,LT> ejbLayerFactory;
    
    public DbLayerInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, final Class<SERVICE> cService, final Class<LT> cLayerType, UtilsSecurityFacade fAcl)
	{       
        this.cLayer = cLayer;
        this.cService = cService;
        this.cLayerType = cLayerType;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
		ejbLayerFactory = EjbGeoLayerFactory.factory(cL, cLayer);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>,
					VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,
					LT extends UtilsStatus<L,D>>
		DbLayerInit<L,D,SERVICE,LAYER,VIEW,VL,LT>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, final Class<SERVICE> cService, final Class<LT> cLayerType, UtilsSecurityFacade fAcl)
	{
		return new DbLayerInit<L,D,SERVICE,LAYER,VIEW,VL,LT>(cL,cD,cLayer,cService,cLayerType,fAcl);
	}

	public void iuLayer(Layers layers, String[] langKeys) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Layers.class.getSimpleName()+" with "+layers.getLayer().size()+" "+Layer.class.getSimpleName());
		
		AhtDbEjbUpdater<LAYER> updateLayer = AhtDbEjbUpdater.createFactory(cLayer);
		
		updateLayer.dbEjbs(fSecurity.all(cLayer));

		for(Layer layer : layers.getLayer())
		{
			updateLayer.actualAdd(layer.getCode());
			
			GeoJsfLayerType.Code layerTypeCode = null;
			LT type;
			SERVICE service;
			
			if(layer.isSetLayer())
			{
				if(layer.getLayer().isSetDbLayer()){layerTypeCode = GeoJsfLayerType.Code.DB;}	
			}
			if(layerTypeCode==null){layerTypeCode = GeoJsfLayerType.Code.UNKNOWN;}
			
			try
			{
				service = fSecurity.fByCode(cService, layer.getService().getCode());
				type = fSecurity.fByCode(cLayerType, layerTypeCode.toString());
			}
			catch (UtilsNotFoundException e1) {throw new UtilsConfigurationException(e1.getMessage());}
			
			
			LAYER ejb;
			try
			{
				ejb = fSecurity.fByCode(cLayer,layer.getCode());
				ejbLangFactory.rmLang(fSecurity,ejb);
				ejbDescriptionFactory.rmDescription(fSecurity,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = ejbLayerFactory.create(layer.getCode(), service, type, langKeys);					
					ejb = (LAYER)fSecurity.persist(ejb);
				}

				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsIntegrityException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(layer.getLangs()));
//				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				
				ejb.setService(fSecurity.fByCode(cService, layer.getService().getCode()));
				
				ejb=(LAYER)fSecurity.update(ejb);
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}