package org.geojsf.util.db.init;

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
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class GeoJsfDbInit <L extends UtilsLang,D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
							>
	extends AbstractUtilsRest<L,D>
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
    private final Class<SLDTEMPLATE> cSldTemplate;
    
    private UtilsFacade fSecurity;
    private GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
    
    private String[] langKeys;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoSldTemplateFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efSldTemplate;
    
    public GeoJsfDbInit(UtilsFacade fUtils, String[] langKeys,GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo,final Class<L> cLang, final Class<D> cDescription, final Class<SERVICE> cService,final Class<CATEGORY> cCategory,final Class<LAYER> cLayer,final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType, final Class<SLDTEMPLATE> cSldTemplate)
	{   
    	super(fUtils,langKeys,cLang,cDescription);
        this.cService = cService;
        this.cCategory = cCategory;
        this.cLayer=cLayer;
        this.cMap=cMap;
        this.cView=cView;
        this.cVp=cVp;
        
        this.cSldType=cSldType;
        this.cSldTemplate=cSldTemplate;
        
        this.fSecurity=fUtils;
        this.fGeo=fGeo;
        
        this.langKeys=langKeys;
        
        ejbLangFactory = EjbLangFactory.createFactory(cLang);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cDescription);
        efSldTemplate = EjbGeoSldTemplateFactory.factory(cSldTemplate);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
				G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
				CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
				SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>> 
		GeoJsfDbInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(UtilsFacade fUtils,GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo,String[] langKeys,final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService,final Class<CATEGORY> cCategory,final Class<LAYER> cLayer,final Class<MAP> cMap,Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType,final Class<SLDTEMPLATE> cSldTemplate)
	{
		return new GeoJsfDbInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fUtils,langKeys,fGeo,cL,cD,cService,cCategory,cLayer,cMap,cView,cVp,cSldType,cSldTemplate);
	}
		
	public DataUpdate importGeoJsfServices(Repository repository)
	{
		DbServiceInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> serviceInit;
		serviceInit = DbServiceInit.factory(cL,cD,cService,fSecurity);
		try{serviceInit.iuServices(repository);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}
	
	@Override
	public DataUpdate importGeoJsfCategories(Repository categories)
	{
		DbCategoryInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> geoCategoryDbInit;
		geoCategoryDbInit = DbCategoryInit.factory(cL,cD,cCategory,fSecurity);
		try{geoCategoryDbInit.iuServices(categories);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfLayers(Layers layers)
	{
		DbLayerInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> layerInit;
		layerInit = DbLayerInit.factory(cL,cD,cCategory,cService,cLayer,cVp,fSecurity,fGeo);
		try {layerInit.iuLayers(layers, langKeys);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfMaps(Maps maps)
	{
		DbMapInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> viewInit;
		viewInit  = DbMapInit.factory(cL,cD,cLayer,cMap,cView,cVp,fSecurity,fGeo);
		try
		{
			viewInit.iuMaps(maps);
		}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override public DataUpdate importGeoJsfSldTypes(Aht types) {return super.importStatus(cSldType, null, types);}

	@Override public DataUpdate importGeoJsfSldTemplates(Repository templates)
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