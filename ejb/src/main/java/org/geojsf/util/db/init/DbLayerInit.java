package org.geojsf.util.db.init;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoLayerFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfLayerType;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
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

public class DbLayerInit <L extends JeeslLang,D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
							LT extends GeoJsfLayerType<L,D,LT,?>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
							SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,?>
							>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
	private final Class<CATEGORY> cCategory;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    
    private JeeslFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo;
    
    private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    private EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER> ejbLayerFactory;
    
    private DbViewPortInit<LAYER,MAP,VP> dbVpInit;
    
    public DbLayerInit(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
    					GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
    					final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,
    					JeeslFacade fUtils,
    					GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{       
    	this.cCategory = cCategory;
    	this.cService = cService;
        this.cLayer = cLayer;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		efLang = EjbLangFactory.instance(fbMeta.getClassL());
		efDescription = EjbDescriptionFactory.factory(fbMeta.getClassD());
		ejbLayerFactory =fbCore.ejbLayer();
		dbVpInit = DbViewPortInit.factory(fbMeta,cVp,fUtils);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
					LT extends GeoJsfLayerType<L,D,LT,?>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,?>,
					SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>> 
	
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
				GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
					final Class<CATEGORY> cCategory,final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,JeeslFacade fUtils,
					GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{
		return new DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fbCore,fbMeta,cCategory,cService,cLayer,cVp,fUtils,fGeo);
	}

	public void iuLayers(Layers layers, String[] langKeys) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Layers.class.getSimpleName()+" with "+layers.getLayer().size()+" "+Layer.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<LAYER> updateLayer = JeeslDbCodeEjbUpdater.createFactory(cLayer);
		
		updateLayer.dbEjbs(fUtils.all(cLayer));

		for(Layer layer : layers.getLayer())
		{
			updateLayer.handled(layer.getCode());
			
			SERVICE service;
			CATEGORY category;
			try
			{
				service = fUtils.fByCode(cService, layer.getService().getCode());
				category = fUtils.fByCode(cCategory, layer.getCategory().getCode());
			}
			catch (JeeslNotFoundException e1) {throw new UtilsConfigurationException(e1.getMessage());}			
			
			LAYER ejb;
			try
			{
				ejb = fUtils.fByCode(cLayer,layer.getCode());
				efLang.rmLang(fUtils,ejb);
				efDescription.rmDescription(fUtils,ejb);
				
			}
			catch (JeeslNotFoundException e)
			{
				try
				{
					ejb = ejbLayerFactory.build(layer.getCode(), service, category, langKeys);					
					ejb = (LAYER)fUtils.persist(ejb);
				}

				catch (JeeslConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
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
					ejb = fGeo.load(ejb);
					dbVpInit.iuViewPort(ejb,layer.getViewPort());
				}
			}
			catch (JeeslConstraintViolationException e) {logger.error("",e);}
			catch (JeeslLockingException e) {logger.error("",e);}
			catch (JeeslNotFoundException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
	}
}