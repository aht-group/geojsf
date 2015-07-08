package org.geojsf.util.db.init;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.rest.db.GeoJsfDatabaseImportRest;
import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.geojsf.web.rest.AbstractGeoJsfRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDbInit <L extends UtilsLang,
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
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	extends AbstractGeoJsfRest<L,D>
	implements GeoJsfDatabaseImportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDbInit.class);
	
    private final Class<SERVICE> cService;
    private final Class<CATEGORY> cCategory;
    private final Class<LAYER> cLayer;
    private final Class<MAP> cMap;
    private final Class<VIEW> cView;
    private final Class<VP> cVp;
    
    private final Class<SLDTYPE> cSldType;
    private final Class<SLDSTYLE> cSldStyle;
    private final Class<SLDTEMPLATE> cSldTemplate;
    
    private UtilsFacade fSecurity;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
    
    private String[] langKeys;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoSldTemplateFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> efSldTemplate;
    
    public GeoJsfDbInit(UtilsFacade fUtils, String[] langKeys,GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo,final Class<L> cLang, final Class<D> cDescription, final Class<SERVICE> cService,final Class<CATEGORY> cCategory,final Class<LAYER> cLayer,final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType, final Class<SLDSTYLE> cSldStyle, final Class<SLDTEMPLATE> cSldTemplate)
	{   
    	super(fUtils,langKeys,cLang,cDescription);
        this.cService = cService;
        this.cCategory = cCategory;
        this.cLayer=cLayer;
        this.cMap=cMap;
        this.cView=cView;
        this.cVp=cVp;
        
        this.cSldType=cSldType;
        this.cSldStyle=cSldStyle;
        this.cSldTemplate=cSldTemplate;
        
        this.fSecurity=fUtils;
        this.fGeo=fGeo;
        
        this.langKeys=langKeys;
        
        ejbLangFactory = EjbLangFactory.createFactory(cLang);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cDescription);
        efSldTemplate = EjbGeoSldTemplateFactory.factory(cSldTemplate);
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
				SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
				SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
				SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>> 
		GeoJsfDbInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
		factory(UtilsFacade fUtils,GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo,String[] langKeys,final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService,final Class<CATEGORY> cCategory,final Class<LAYER> cLayer,final Class<MAP> cMap,Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType,final Class<SLDSTYLE> cSldStyle,final Class<SLDTEMPLATE> cSldTemplate)
	{
		return new GeoJsfDbInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(fUtils,langKeys,fGeo,cL,cD,cService,cCategory,cLayer,cMap,cView,cVp,cSldType,cSldStyle,cSldTemplate);
	}
		
	public DataUpdate importGeoJsfServices(Repository repository)
	{
		DbServiceInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> serviceInit;
		serviceInit = DbServiceInit.factory(cL,cD,cService,fSecurity);
		try{serviceInit.iuServices(repository);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}
	
	@Override
	public DataUpdate importGeoJsfCategories(Repository categories)
	{
		DbCategoryInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> geoCategoryDbInit;
		geoCategoryDbInit = DbCategoryInit.factory(cL,cD,cCategory,fSecurity);
		try{geoCategoryDbInit.iuServices(categories);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfLayers(Layers layers)
	{
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> layerInit;
		layerInit = DbLayerInit.factory(cL,cD,cCategory,cService,cLayer,cVp,fSecurity,fGeo);
		try {layerInit.iuLayers(layers, langKeys);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfMaps(Maps maps)
	{
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> viewInit;
		viewInit  = DbMapInit.factory(cL,cD,cLayer,cMap,cView,cVp,fSecurity,fGeo);
		try
		{
			viewInit.iuMaps(maps);
		}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override public DataUpdate importGeoJsfSldTypes(Aht types) {return super.importStatus(cSldType, null, types);}
	@Override public DataUpdate importGeoJsfSldStyles(Aht styles)
	{
		logger.trace("importGeoJsfSldStyles "+cSldStyle.getName());
		return super.importStatus(cSldStyle, null, styles);
	}

	@Override
	public DataUpdate importGeoJsfSldTemplates(Repository templates)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cSldTemplate.getName(),"DB Import"));
		for(SldTemplate xTemplate : templates.getSldTemplate())
		{
			SLDTEMPLATE eTemplate = null;
			try
			{
				eTemplate = fSecurity.fByCode(cSldTemplate,xTemplate.getCode());
				ejbLangFactory.rmLang(fSecurity,eTemplate);
				ejbDescriptionFactory.rmDescription(fSecurity,eTemplate);
				
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					SLDTYPE type = fSecurity.fByCode(cSldType, xTemplate.getType().getCode());
					eTemplate = efSldTemplate.build(type,xTemplate.getCode());
					eTemplate = fSecurity.persist(eTemplate);
				}

				catch (UtilsConstraintViolationException e1) {dut.fail(e1,true);}
				catch (UtilsNotFoundException e1) {dut.fail(e1,true);}
			}
			
			if(eTemplate!=null && eTemplate.getId()>0)
			{
				try
				{
					eTemplate.setName(ejbLangFactory.getLangMap(xTemplate.getLangs()));
					eTemplate.setDescription(ejbDescriptionFactory.create(xTemplate.getDescriptions()));
					
					eTemplate = fSecurity.update(eTemplate);
					dut.success();
				}
				catch (UtilsConstraintViolationException e) {dut.fail(e,true);}
				catch (UtilsLockingException e) {dut.fail(e,true);}
			}
		}
		return dut.toDataUpdate();
	}
}