package org.geojsf.util.db.init;

import java.util.List;

import org.geojsf.factory.ejb.EjbGeoViewFactory;
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
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.View;
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

public class DbMapInit <L extends UtilsLang,D extends UtilsDescription,
G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
						CATEGORY extends GeoJsfCategory<L,D,LAYER>,
						SERVICE extends GeoJsfService<L,D,LAYER>,
						LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
						MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
						SCALE extends GeoJsfScale<L,D>, 
						VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
						VP extends GeoJsfViewPort,
						DS extends GeoJsfDataSource<L,D,LAYER>,
						SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
						RULE extends GeoJsfSldRule<L,D,G>
						>
{
	final static Logger logger = LoggerFactory.getLogger(DbMapInit.class);
	
	private final Class<MAP> cMap;
    private final Class<LAYER> cLayer;
    private final Class<VIEW> cView;
    
    private UtilsFacade fUtils;
    private GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoViewFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efView;
    
    private DbViewPortInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> dbVpInit;
    
    public DbMapInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView, final Class<VP> cVp, UtilsFacade fUtils, GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{       
        this.cLayer = cLayer;
        this.cMap = cMap;
        this.cView = cView;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		ejbLangFactory = EjbLangFactory.factory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.factory(cD);
		efView = EjbGeoViewFactory.factory(cView);
		
		dbVpInit = DbViewPortInit.factory(cVp,fUtils);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>> 
		DbMapInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,UtilsFacade fUtils,GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		return new DbMapInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cL,cD,cLayer,cMap,cView,cVp,fUtils,fGeo);
	}
	
	public void iuMaps(Maps maps) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Maps.class.getSimpleName()+" with "+maps.getMap()+" "+Map.class.getSimpleName());
		
		JeeslDbCodeEjbUpdater<MAP> ejbUpdater = JeeslDbCodeEjbUpdater.createFactory(cMap);
		ejbUpdater.dbEjbs(fUtils.all(cMap));

		for(Map map : maps.getMap())
		{
			ejbUpdater.actualAdd(map.getCode());
			
			MAP ejb;
			try
			{
				ejb = fUtils.fByCode(cMap,map.getCode());
				ejbLangFactory.rmLang(fUtils,ejb);
				ejbDescriptionFactory.rmDescription(fUtils,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = cMap.newInstance();
					ejb.setCode(map.getCode());
					ejb = (MAP)fUtils.persist(ejb);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(map.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(map.getDescriptions()));
				ejb=fUtils.update(ejb);
				
				iuViews(ejb,map.getView());
				if(map.isSetViewPort())
				{
					ejb = fGeo.load(cMap,ejb);
					dbVpInit.iuViewPort(ejb,map.getViewPort());
				}
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		ejbUpdater.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	private void iuViews(MAP ejbMap, List<View> views) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		ejbMap = fGeo.load(cMap, ejbMap);
		logger.trace("Views: "+ejbMap.getViews().size());
		
		for(VIEW vl : ejbMap.getViews())
		{
			fGeo.rm(cView, vl);
		}
		ejbMap = fGeo.load(cMap, ejbMap);
		logger.trace("Layer: "+ejbMap.getViews().size());
		
		int i=1;
		for(View view : views)
		{
			LAYER l = fUtils.fByCode(cLayer, view.getLayer().getCode());

			VIEW vl = efView.create(ejbMap, l, i, (view.isSetVisible() && view.isVisible()), (view.isSetLegend() && view.isLegend()));
			fUtils.persist(vl);
			i++;
		}
		ejbMap = fUtils.update(ejbMap);
		logger.trace("Layer: "+ejbMap.getViews().size());
	}
}