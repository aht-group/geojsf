package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfProvideSldStatus;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSldLibraryBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicComponent<L,D,G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									SCALE extends GeoJsfScale<L,D>,
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									DS extends GeoJsfDataSource<L,D,LAYER>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<JL,L,D,?>>
		extends AbstractGeoJsfBean<L,D,LOC,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,JSON,JQ,JL>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldLibraryBean.class);
	
	private GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld;
	
	private List<SLDTYPE> types; public List<SLDTYPE> getTypes() {return types;}
	private List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	private List<SLD> slds; public List<SLD> getSlds() {return slds;} public void setSlds(List<SLD> slds) {this.slds = slds;}
	private List<String> sldStatusClasses; public List<String> getSldStatusClasses() {return sldStatusClasses;}
	
	private SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}

	public AbstractSldLibraryBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,DS,VP,JSON,JQ,JL> fbMeta,
									GeoSldFactoryBuilder<L,D,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbCore,fbMeta,fbSld);
		
		sldStatusClasses = new ArrayList<String>();
	}
	
	protected void postConstructSldLibrary(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo,
										GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld)
	{
		super.postConstructGeojsf(bTranslation,bMessage,fGeo);
		this.fSld=fSld;
		templates = fGeo.all(fbSld.getClassTemplate());
		types = fGeo.allOrderedPositionVisible(fbSld.getClassSldType());
	}
	
	public void cancelSld(){reset(true);}
	private void reset(boolean rSld)
	{
		if(rSld){sld=null;}
	}

	protected void reloadSlds()
	{
		slds = fSld.fLibrarySlds();
	}

	public void selectSld()
	{
		logger.info(AbstractLogMessage.selectEntity(sld));
		sld = fSld.load(sld);
	}
	
	public void addSld()
	{
		logger.info(AbstractLogMessage.addEntity(fbSld.getClassSld()));
		sld = efSld.build(null,true);
		sld.setName(efLang.createEmpty(bTranslation.getLocales()));
		sld.setDescription(efDescription.createEmpty(bTranslation.getLocales()));
	}
	
	public void saveSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("1: "+sld.getStatusAttribute());
		sld.setType(fGeo.find(fbSld.getClassSldType(),sld.getType()));
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.template.toString())){sld.setTemplate(null);}
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.status.toString())){sld.setStatusClass(null);}
//		
		if(sld.getTemplate()!=null){sld.setTemplate(fGeo.find(fbSld.getClassTemplate(),sld.getTemplate()));}
		
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld = fGeo.save(sld);
		logger.info("2: "+sld.getStatusAttribute());
		reloadSlds();
	}
	
	public void deleteSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
		fGeo.rm(sld);
		reloadSlds();
		reset(true);
	}
	
	public void changeTYPE()
	{
		sld.setType(fGeo.find(fbSld.getClassSldType(), sld.getType()));
		logger.info(AbstractLogMessage.selectOneMenuChange(sld.getType()));
	}
	
	protected <C extends GeoJsfProvideSldStatus> void activateSldType(Class<C> c)
	{
		sldStatusClasses.add(c.getName());
	}
}