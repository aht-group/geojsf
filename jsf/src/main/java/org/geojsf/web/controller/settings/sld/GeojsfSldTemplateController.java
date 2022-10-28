package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.web.controller.AbstractJeeslWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfSldTemplateController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											TEMPLATE extends GeoJsfSldTemplate<L,D>,
											TYPE extends JeeslStatus<L,D,TYPE>,
											SLD extends GeoJsfSld<L,D,TEMPLATE,TYPE,RULE>,
											RULE extends GeoJsfSldRule<L,D,?>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfSldTemplateController.class);
	
	private GeoSldFacade<L,D,TEMPLATE,TYPE,SLD,RULE> fSld;
	
	private final GeoSldFactoryBuilder<L,D,?,?,TEMPLATE,TYPE,SLD,RULE> fbSld;
	
	protected final EjbGeoSldTemplateFactory<TEMPLATE> efTemplate;
	
	protected List<TEMPLATE> templates; public List<TEMPLATE> getTemplates(){return templates;}
	protected TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}
	
	public GeojsfSldTemplateController(GeoSldFactoryBuilder<L,D,?,?,TEMPLATE,TYPE,SLD,RULE> fbSld)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.fbSld=fbSld;
		efTemplate = fbSld.efTemplate();
	}
	
	public void postConstructSldTemplate(GeoSldFacade<L,D,TEMPLATE,TYPE,SLD,RULE> fSld,
											JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessag)
	{
		super.postConstructWebController(lp,bMessage);
		this.fSld=fSld;
	}
	
	protected void reloadTemplates()
	{
		templates = fSld.all(fbSld.getClassTemplate());
	}
	
	public void selectTemplate() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(template));
		template = efLang.persistMissingLangs(fSld,lp.getLocales(),template);
		template = efDescription.persistMissingLangs(fSld,lp.getLocales(),template);
	}
	
	public void addTemplate()
	{
		logger.info(AbstractLogMessage.addEntity(fbSld.getClassTemplate()));
		template = efTemplate.build(null);
		template.setName(efLang.buildEmpty(lp.getLocales()));
		template.setDescription(efDescription.buildEmpty(lp.getLocales()));
	}
	
	public void cancelTemplate()
	{
		template=null;
	}
	
	public void saveTemplate() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(template));
		template = fSld.save(template);
		reloadTemplates();
	}
}