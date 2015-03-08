package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMapSldBean<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapSldBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoSldTemplateFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> efTemplate;
	
	protected GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fGeo;
	
	private String[] langKeys;
	private Class<SLDTEMPLATE> cTemplate;
	private Class<SLDTYPE> cType;
	
	public void initSuper(String[] langKeys, final Class<L> cLang, final Class<D> clDescription,final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cType)
	{
		this.langKeys=langKeys;
		this.cTemplate=cTemplate;
		this.cType=cType;
		
		efLang = EjbLangFactory.createFactory(cLang);
		efDescription = EjbDescriptionFactory.createFactory(clDescription);
		efTemplate = EjbGeoSldTemplateFactory.factory(cTemplate);
	}
	
	//TEMPLATES
	protected List<SLDTEMPLATE> templates;
	public List<SLDTEMPLATE> getTemplates(){return templates;}
	
	protected void reloadTemplates()
	{
		templates = fGeo.all(cTemplate);
	}
	
	//TYPES
	private List<SLDTYPE> types;
	public List<SLDTYPE> getTypes() {return types;}
	
	protected void reloadTypes()
	{
		types = fGeo.all(cType);
	}
	
	//TEMPLATE
	protected SLDTEMPLATE template;
	public SLDTEMPLATE getTemplate() {return template;}
	public void setTemplate(SLDTEMPLATE template) {this.template = template;}
	
	public void selectTemplate() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(template));
		missingLangsMap();
	}
	protected void missingLangsMap(){}
	
	public void addTemplate()
	{
		logger.info(AbstractLogMessage.addEntity(cTemplate));
		template = efTemplate.build(null,null);
		try
		{
			template.setName(efLang.createEmpty(langKeys));
			template.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
	}
	
	public void cancelTemplate()
	{
		template=null;
	}
	
	public void saveTemplate() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(template));
		template.setType(fGeo.find(cType, template.getType()));
		template = fGeo.save(template);
		reloadTemplates();
	}
}