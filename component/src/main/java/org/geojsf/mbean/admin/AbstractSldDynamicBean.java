package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSldDynamicBean <L extends UtilsLang,
									D extends UtilsDescription,
									G extends UtilsGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>,
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
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldDynamicBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoSldFactory<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efSld;
	
	private GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTEMPLATE,SLDTYPE> fGeo;
	
	private String[] langKeys;
	private Class<SLD> cSld;
	private Class<SLDTEMPLATE> cTemplate;
	
	protected List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	protected List<SLD> slds; public List<SLD> getSlds() {return slds;} public void setSlds(List<SLD> slds) {this.slds = slds;}
	
	public void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTEMPLATE,SLDTYPE> fGeo, final Class<L> cLang, final Class<D> clDescription,final Class<SLD> cSld, final Class<SLDTYPE> cType,final Class<SLDTEMPLATE> cTemplate)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		this.cSld=cSld;
		this.cTemplate=cTemplate;
		
		efLang = EjbLangFactory.createFactory(cLang);
		efDescription = EjbDescriptionFactory.createFactory(clDescription);
		efSld = EjbGeoSldFactory.factory(cSld);
				
		templates = fGeo.all(cTemplate);
	}

	protected void reloadSlds()
	{
		slds = fGeo.fGlobalSlds(cSld);
	}
	
	//TEMPLATE
	protected SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}
	
	public void selectSld() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(sld));
	}
	
	public void addSld()
	{
		logger.info(AbstractLogMessage.addEntity(cSld));
		sld = efSld.build(null,true);
		sld.setName(efLang.createEmpty(langKeys));
		sld.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void cancelSld()
	{
		sld=null;
	}
	
	public void saveSld() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld.setTemplate(fGeo.find(cTemplate, sld.getTemplate()));
		sld = fGeo.save(sld);
		reloadSlds();
	}
	
	public void rmSld() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
	}
}