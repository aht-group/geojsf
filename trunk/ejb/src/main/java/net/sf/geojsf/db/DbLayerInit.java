package net.sf.geojsf.db;

import java.util.Map;

import net.sf.ahtutils.controller.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.xml.openlayers.Layer;
import net.sf.geojsf.xml.openlayers.Layers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbLayerInit <L extends UtilsLang,
 								D extends UtilsDescription,
 								LAYER extends GeoJsfLayer<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
    private final Class<L> cL;
    private final Class<D> cD;
    private final Class<LAYER> cLayer;
    
    private UtilsSecurityFacade fSecurity;
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public DbLayerInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, UtilsSecurityFacade fAcl)
	{       
        this.cL = cL;
        this.cD = cD;
        this.cLayer = cLayer;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	

	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   LAYER extends GeoJsfLayer<L,D>>
		DbLayerInit<L,D,LAYER>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, UtilsSecurityFacade fAcl)
	{
		return new DbLayerInit<L,D,LAYER>(cL,cD,cLayer,fAcl);
	}
	
	
	
	public void iuLayer(Layers layers) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Layers.class.getSimpleName()+" with "+layers.getLayer().size()+" "+Layer.class.getSimpleName());
		
		AhtDbEjbUpdater<LAYER> updateLayer = AhtDbEjbUpdater.createFactory(cLayer);
		
		updateLayer.dbEjbs(fSecurity.all(cLayer));

		for(Layer layer : layers.getLayer())
		{
			updateLayer.actualAdd(layer.getCode());
			
			LAYER ejb;
			try
			{
				ejb = fSecurity.fByCode(cLayer,layer.getCode());
				rmLang(ejb);
				rmDescription(ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cLayer.newInstance();
					ejb.setCode(layer.getCode());
					ejb = (LAYER)fSecurity.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
//				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				ejb=(LAYER)fSecurity.update(ejb);
				
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
//			catch (InstantiationException e) {logger.error("",e);}
//			catch (IllegalAccessException e) {logger.error("",e);}
//			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	protected <M extends EjbWithLang<L>> void rmLang(M ejb)
	{
		Map<String,L> langMap = ejb.getName();
		ejb.setName(null);
		
		try{ejb=fSecurity.update(ejb);}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
		
		for(L lang : langMap.values())
		{
			try {fSecurity.rm(lang);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
	}
	
	protected <M extends EjbWithDescription<D>> void rmDescription(M ejb)
	{
		Map<String,D> descMap = ejb.getDescription();
		ejb.setDescription(null);
		
		try{ejb=fSecurity.update(ejb);}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
		
		for(D desc : descMap.values())
		{
			try {fSecurity.rm(desc);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
	}

}