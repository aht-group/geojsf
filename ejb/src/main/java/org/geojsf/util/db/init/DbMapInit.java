package org.geojsf.util.db.init;

import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
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
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.View;
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
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbMapInit <L extends JeeslLang,D extends JeeslDescription,
						CATEGORY extends GeoJsfCategory<L,D,LAYER>,
						SERVICE extends GeoJsfService<L,D,LAYER>,
						LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
						MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
						SCALE extends GeoJsfScale<L,D>, 
						VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
						VP extends GeoJsfViewPort,
						DS extends GeoJsfDataSource<L,D,LAYER>,
						SLDTEMPLATE extends GeoJsfSldXml<L,D>,
						SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
						SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
						RULE extends GeoJsfSldRule<L,D,?>
						>
{
	final static Logger logger = LoggerFactory.getLogger(DbMapInit.class);
	
	private final Class<MAP> cMap;
    private final Class<LAYER> cLayer;
    private final Class<VIEW> cView;
    
    private JeeslFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> efView;
    
    private DbViewPortInit<LAYER,MAP,VP> dbVpInit;
    
    public DbMapInit(final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
    				 final GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
    				 final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView, final Class<VP> cVp, JeeslFacade fUtils,
    				 GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{       
        this.cLayer = cLayer;
        this.cMap = cMap;
        this.cView = cView;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		ejbLangFactory = EjbLangFactory.instance(fbMeta.getClassL());
		ejbDescriptionFactory = EjbDescriptionFactory.factory(fbMeta.getClassD());
		efView = fbCore.ejbView();
		
		dbVpInit = DbViewPortInit.factory(fbMeta,cVp,fUtils);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,?>,
					SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
					SLDTEMPLATE extends GeoJsfSldXml<L,D>> 
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
				final GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
				final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,JeeslFacade fUtils,
				GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo)
	{
		return new DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fbCore,fbMeta,cLayer,cMap,cView,cVp,fUtils,fGeo);
	}
	
	public void iuMaps(Maps maps) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Maps.class.getSimpleName()+" with "+maps.getMap()+" "+Map.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<MAP> ejbUpdater = JeeslDbCodeEjbUpdater.createFactory(cMap);
		ejbUpdater.dbEjbs(fUtils.all(cMap));

		for(Map map : maps.getMap())
		{
			ejbUpdater.handled(map.getCode());
			
			MAP ejb;
			try
			{
				ejb = fUtils.fByCode(cMap,map.getCode());
				ejbLangFactory.rmLang(fUtils,ejb);
				ejbDescriptionFactory.rmDescription(fUtils,ejb);
			}
			catch (JeeslNotFoundException e)
			{
				try
				{
					ejb = cMap.newInstance();
					ejb.setCode(map.getCode());
					ejb = (MAP)fUtils.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (JeeslConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(map.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(map.getDescriptions()));
				ejb=fUtils.save(ejb);
				
				iuViews(ejb,map.getView());
				if(map.isSetViewPort())
				{
					ejb = fGeo.load(ejb);
					dbVpInit.iuViewPort(ejb,map.getViewPort());
				}
			}
			catch (JeeslConstraintViolationException e) {logger.error("",e);}
			catch (JeeslLockingException e) {logger.error("",e);}
			catch (JeeslNotFoundException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	private void iuViews(MAP ejbMap, List<View> views) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		ejbMap = fGeo.load(ejbMap);
		logger.trace("Views: "+ejbMap.getViews().size());
		
		for(VIEW vl : ejbMap.getViews())
		{
			fGeo.rm(cView, vl);
		}
		ejbMap = fGeo.load(ejbMap);
		logger.trace("Layer: "+ejbMap.getViews().size());
		
		int i=1;
		for(View view : views)
		{
			LAYER l = fUtils.fByCode(cLayer, view.getLayer().getCode());

			VIEW vl = efView.create(ejbMap, l, i, (view.isSetVisible() && view.isVisible()), (view.isSetLegend() && view.isLegend()));
			fUtils.persist(vl);
			i++;
		}
		ejbMap = fUtils.save(ejbMap);
		logger.trace("Layer: "+ejbMap.getViews().size());
	}
}