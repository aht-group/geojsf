package org.geojsf.util.db.init;

import org.geojsf.api.rest.db.GeoJsfDatabaseImportRest;
import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
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
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.jeesl.controller.monitoring.counter.DataUpdateTracker;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.xml.io.locale.status.XmlTypeFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Aht;
import org.jeesl.model.xml.io.ssi.sync.DataUpdate;

public class GeoJsfDbInit <L extends JeeslLang,D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
							LT extends GeoJsfLayerType<L,D,LT,?>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SDX extends GeoJsfSldXml<L,D,SLD>,
							SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
							SLD extends GeoJsfSld<L,D,SDX,SLDTYPE,RULE,?,?>,
							RULE extends GeoJsfSldRule<L,D,?>
							>
	extends AbstractUtilsRest<L,D>
	implements GeoJsfDatabaseImportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDbInit.class);
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore;
	private final GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta;
	
    private final Class<LAYER> cLayer;
    private final Class<MAP> cMap;
    private final Class<VIEW> cView;
    private final Class<VP> cVp;
    
    private final Class<SLDTYPE> cSldType;
    private final Class<SDX> cSldTemplate;
    
    private JeeslFacade fSecurity;
    private GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo;
    
    private String[] langKeys;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    private EjbGeoSldTemplateFactory<SDX> efSldTemplate;
    
    public GeoJsfDbInit(final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
    					final GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
    					final GeoSldFactoryBuilder<L,D,LAYER,SDX,SLD,SLDTYPE,RULE,?,?> fbSld,
    					JeeslFacade fUtils, String[] langKeys,
    					
    					GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo,
    					final Class<LAYER> cLayer,final Class<MAP> cMap,final Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType, final Class<SDX> cSldTemplate)
	{   
    	super(fUtils,langKeys,fbCore.getClassL(),fbCore.getClassD());
    	this.fbCore=fbCore;
    	this.fbMeta=fbMeta;
    	
        this.cLayer=cLayer;
        this.cMap=cMap;
        this.cView=cView;
        this.cVp=cVp;
        
        this.cSldType=cSldType;
        this.cSldTemplate=cSldTemplate;
        
        this.fSecurity=fUtils;
        this.fGeo=fGeo;
        
        this.langKeys=langKeys;
        
        ejbLangFactory = EjbLangFactory.instance(fbCore.getClassL());
		ejbDescriptionFactory = EjbDescriptionFactory.factory(fbCore.getClassD());
        efSldTemplate = fbSld.efTemplate();
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
				SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE,?,?>,
				RULE extends GeoJsfSldRule<L,D,?>,
				SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
				SLDTEMPLATE extends GeoJsfSldXml<L,D,SLD>> 
		GeoJsfDbInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
				final GeoMetaFactoryBuilder<L,D,LAYER,DS,VP,SCALE,?> fbMeta,
				final GeoSldFactoryBuilder<L,D,LAYER,SLDTEMPLATE,SLD,SLDTYPE,RULE,?,?> fbSld,
				JeeslFacade fUtils,
				GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS> fGeo,
				String[] langKeys,final Class<L> cL, final Class<D> cD, final Class<SERVICE> cService,final Class<CATEGORY> cCategory,final Class<LAYER> cLayer,final Class<MAP> cMap,Class<VIEW> cView,final Class<VP> cVp,final Class<SLDTYPE> cSldType,final Class<SLDTEMPLATE> cSldTemplate)
	{
		return new GeoJsfDbInit<>(fbCore,fbMeta,fbSld,fUtils,langKeys,fGeo,cLayer,cMap,cView,cVp,cSldType,cSldTemplate);
	}
		
	public DataUpdate importGeoJsfServices(Repository repository)
	{
		DbServiceInit<L,D,SERVICE> serviceInit;
		serviceInit = DbServiceInit.factory(cL,cD,fbCore.getClassService(),fSecurity);
		try{serviceInit.iuServices(repository);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}
	
	@Override
	public DataUpdate importGeoJsfCategories(Repository categories)
	{
		DbCategoryInit<L,D,CATEGORY> geoCategoryDbInit;
		geoCategoryDbInit = DbCategoryInit.factory(cL,cD,fbCore.getClassCategory(),fSecurity);
		try{geoCategoryDbInit.iuServices(categories);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfLayers(Layers layers)
	{
		DbLayerInit<L,D,CATEGORY,SERVICE,LAYER,LT,MAP,SCALE,VIEW,VP,DS,SDX,SLDTYPE,SLD,RULE> layerInit;
		layerInit = DbLayerInit.factory(fbCore,fbMeta,fbCore.getClassCategory(),fbCore.getClassService(),cLayer,cVp,fSecurity,fGeo);
		try {layerInit.iuLayers(layers, langKeys);}
		catch (UtilsConfigurationException e) {e.printStackTrace();}
		return new DataUpdate();
	}

	@Override
	public DataUpdate importGeoJsfMaps(Maps maps)
	{
		DbMapInit<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SDX,SLDTYPE,SLD,RULE> viewInit;
		viewInit  = DbMapInit.factory(fbCore,fbMeta,cLayer,cMap,cView,cVp,fSecurity,fGeo);
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
			SDX eTemplate = null;
			try
			{
				eTemplate = fSecurity.fByCode(cSldTemplate,xTemplate.getCode());
				ejbLangFactory.rmLang(fSecurity,eTemplate);
				ejbDescriptionFactory.rmDescription(fSecurity,eTemplate);
				
			}
			catch (JeeslNotFoundException e)
			{
				try
				{
//					SLDTYPE type = fSecurity.fByCode(cSldType, xTemplate.getType().getCode());
					eTemplate = efSldTemplate.build(xTemplate.getCode());
					eTemplate = fSecurity.persist(eTemplate);
				}

				catch (JeeslConstraintViolationException e1) {dut.fail(e1,true);}
//				catch (JeeslNotFoundException e1) {dut.fail(e1,true);}
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
				catch (JeeslConstraintViolationException e) {dut.fail(e,true);}
				catch (JeeslLockingException e) {dut.fail(e,true);}
			}
		}
		return dut.toDataUpdate();
	}
}