package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
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
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractMapScaleBean <L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
									CATEGORY extends GeoJsfCategory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SERVICE extends GeoJsfService<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									LAYER extends GeoJsfLayer<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									MAP extends GeoJsfMap<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SCALE extends GeoJsfScale<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
									VIEW extends GeoJsfView<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									VP extends GeoJsfViewPort<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									DS extends GeoJsfDataSource<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
									>
		extends AbstractGeoJsfBean<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapScaleBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoSldTemplateFactory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efTemplate;
	
	private GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	private String[] langKeys;
	private Class<SLDTEMPLATE> cTemplate;
	
	protected List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	protected SLDTEMPLATE template; public SLDTEMPLATE getTemplate() {return template;} public void setTemplate(SLDTEMPLATE template) {this.template = template;}
	
	public AbstractMapScaleBean(final Class<SLDTEMPLATE> cTemplate, final Class<SLD> cSld)
	{
		super(cSld);
	}
	
	public void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo, final Class<L> cLang, final Class<D> clDescription,final Class<SLDTYPE> cType, final Class<SLDTEMPLATE> cTemplate)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		this.cTemplate=cTemplate;
		
		efLang = EjbLangFactory.createFactory(cLang);
		efDescription = EjbDescriptionFactory.createFactory(clDescription);
		efTemplate = EjbGeoSldTemplateFactory.factory(cTemplate);
		

	}
	
	protected void reloadTemplates()
	{
		templates = fGeo.all(cTemplate);
	}
	
	public void selectTemplate() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(template));
		missingLangsMap();
	}
	protected void missingLangsMap(){}
	
	public void addTemplate()
	{
		logger.info(AbstractLogMessage.addEntity(cTemplate));
		template = efTemplate.build(null);
		template.setName(efLang.createEmpty(langKeys));
		template.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void cancelTemplate()
	{
		template=null;
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(template));
		template = fGeo.save(template);
		reloadTemplates();
	}
}