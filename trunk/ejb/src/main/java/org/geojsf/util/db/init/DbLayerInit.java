package org.geojsf.util.db.init;

import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbLayerInit <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
	private final Class<CATEGORY> cCategory;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    
    private UtilsFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
    
    private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    private EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE> ejbLayerFactory;
    
    private DbViewPortInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE> dbVpInit;
    
    public DbLayerInit(final Class<L> cL, final Class<D> cD,final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,UtilsFacade fUtils, GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo)
	{       
    	this.cCategory = cCategory;
    	this.cService = cService;
        this.cLayer = cLayer;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
		ejbLayerFactory = EjbGeoLayerFactory.factory(cL, cLayer);
		dbVpInit = DbViewPortInit.factory(cVp,fUtils);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
		factory(final Class<L> cL, final Class<D> cD,final Class<CATEGORY> cCategory,final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,UtilsFacade fUtils,GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo)
	{
		return new DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(cL,cD,cCategory,cService,cLayer,cVp,fUtils,fGeo);
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
			CATEGORY category;
			try
			{
				service = fUtils.fByCode(cService, layer.getService().getCode());
				category = fUtils.fByCode(cCategory, layer.getCategory().getCode());
			}
			catch (UtilsNotFoundException e1) {throw new UtilsConfigurationException(e1.getMessage());}			
			
			LAYER ejb;
			try
			{
				ejb = fUtils.fByCode(cLayer,layer.getCode());
				efLang.rmLang(fUtils,ejb);
				efDescription.rmDescription(fUtils,ejb);
				
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = ejbLayerFactory.build(layer.getCode(), service, category, langKeys);					
					ejb = (LAYER)fUtils.persist(ejb);
				}

				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			}
			
			try
			{
				ejb.setName(efLang.getLangMap(layer.getLangs()));
				ejb.setDescription(efDescription.create(layer.getDescriptions()));
				
				//TODO can be removed after applied to active projects
				boolean layerSql = false;
				if(layer.isSetSql()){layerSql = layer.isSql();}
				ejb.setSqlLayer(layerSql);
				
				ejb.setTemporalLayer(layer.isTemporal());
				ejb.setService(fUtils.fByCode(cService, layer.getService().getCode()));
				
				ejb=(LAYER)fUtils.update(ejb);
				
				if(layer.isSetViewPort())
				{
					ejb = fGeo.load(cLayer,ejb);
					dbVpInit.iuViewPort(ejb,layer.getViewPort());
				}
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}