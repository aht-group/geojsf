package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.web.AbstractJeeslLocaleWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeoSldXmlWebController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											SXD extends GeoJsfSldXml<L,D,SLD>,
											TYPE extends GeoJsfSldType<L,D,TYPE,?>,
											SLD extends GeoJsfSld<L,D,SXD,TYPE,RULE,?,?>,
											RULE extends GeoJsfSldRule<L,D,?>>
		extends AbstractJeeslLocaleWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoSldXmlWebController.class);
	
	private GeoSldFacade<L,D,SXD,SLD,TYPE,RULE> fSld;
	
	private final GeoSldFactoryBuilder<L,D,?,SXD,SLD,TYPE,RULE,?,?> fbSld;
	
	protected final EjbGeoSldTemplateFactory<SXD> efTemplate;
	
	protected List<SXD> templates; public List<SXD> getTemplates(){return templates;}
	protected SXD template; public SXD getTemplate() {return template;} public void setTemplate(SXD template) {this.template = template;}
	
	public GeoSldXmlWebController(GeoSldFactoryBuilder<L,D,?,SXD,SLD,TYPE,RULE,?,?> fbSld)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.fbSld=fbSld;
		efTemplate = fbSld.efTemplate();
	}
	
	public void postConstructSldTemplate(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessag,
										GeoSldFacade<L,D,SXD,SLD,TYPE,RULE> fSld)
	{
		super.postConstructLocaleWebController(lp,bMessage);
		this.fSld=fSld;
		this.reloadTemplates();
	}
	
	protected void reloadTemplates()
	{
		templates = fSld.all(fbSld.getClassTemplate());
	}
	
	public void selectTemplate() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(template));
		template = efLang.persistMissingLangs(fSld,lp,template);
		template = efDescription.persistMissingLangs(fSld,lp.getLocales(),template);
	}
	
	public void addTemplate()
	{
		logger.info(AbstractLogMessage.createEntity(fbSld.getClassTemplate()));
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