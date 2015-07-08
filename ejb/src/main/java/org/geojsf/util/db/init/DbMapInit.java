package org.geojsf.util.db.init;

import java.util.List;

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

import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbMapInit <L extends UtilsLang,
						D extends UtilsDescription,
						CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
						SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
						RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
						SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
						SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(DbMapInit.class);
	
	private final Class<MAP> cMap;
    private final Class<LAYER> cLayer;
    private final Class<VIEW> cView;
    
    private UtilsFacade fUtils;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> efView;
    
    private DbViewPortInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> dbVpInit;
    
    public DbMapInit(final Class<L> cL, final Class<D> cD,final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView, final Class<VP> cVp, UtilsFacade fUtils, GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo)
	{       
        this.cLayer = cLayer;
        this.cMap = cMap;
        this.cView = cView;
        
        this.fUtils=fUtils;
        this.fGeo=fGeo;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
		efView = EjbGeoViewFactory.factory(cView);
		
		dbVpInit = DbViewPortInit.factory(cVp,fUtils);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>> 
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
		factory(final Class<L> cL,final Class<D> cD,final Class<LAYER> cLayer, final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,UtilsFacade fUtils,GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo)
	{
		return new DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(cL,cD,cLayer,cMap,cView,cVp,fUtils,fGeo);
	}
	
	public void iuMaps(Maps maps) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Maps.class.getSimpleName()+" with "+maps.getMap()+" "+Map.class.getSimpleName());
		
		AhtDbEjbUpdater<MAP> ejbUpdater = AhtDbEjbUpdater.createFactory(cMap);
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