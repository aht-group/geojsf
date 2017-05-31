package org.geojsf.util.db.init;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
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
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
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

public class DbLayerInit <L extends UtilsLang,D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SERVICE extends GeoJsfService<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							LAYER extends GeoJsfLayer<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							MAP extends GeoJsfMap<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SCALE extends GeoJsfScale<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
							VIEW extends GeoJsfView<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VP extends GeoJsfViewPort<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							DS extends GeoJsfDataSource<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
							>
{
	final static Logger logger = LoggerFactory.getLogger(DbLayerInit.class);
	
	private final Class<CATEGORY> cCategory;
	private final Class<SERVICE> cService;
    private final Class<LAYER> cLayer;
    
    private UtilsFacade fUtils;
    private GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
    
    private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    private EjbGeoLayerFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> ejbLayerFactory;
    
    private DbViewPortInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> dbVpInit;
    
    public DbLayerInit(final Class<L> cL, final Class<D> cD,final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,UtilsFacade fUtils, GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{       
    	this.cCategory = cCategory;
    	this.cService = cService;
        this.cLayer = cLayer;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		efLang = EjbLangFactory.factory(cL);
		efDescription = EjbDescriptionFactory.factory(cD);
		ejbLayerFactory = EjbGeoLayerFactory.factory(cL, cLayer);
		dbVpInit = DbViewPortInit.factory(cVp,fUtils);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, SCALE extends GeoJsfScale<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
					VIEW extends GeoJsfView<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>> 
	
		DbLayerInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final Class<L> cL, final Class<D> cD,final Class<CATEGORY> cCategory,final Class<SERVICE> cService, final Class<LAYER> cLayer,final Class<VP> cVp,UtilsFacade fUtils,GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		return new DbLayerInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cL,cD,cCategory,cService,cLayer,cVp,fUtils,fGeo);
	}

	public void iuLayers(Layers layers, String[] langKeys) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Layers.class.getSimpleName()+" with "+layers.getLayer().size()+" "+Layer.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<LAYER> updateLayer = JeeslDbCodeEjbUpdater.createFactory(cLayer);
		
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