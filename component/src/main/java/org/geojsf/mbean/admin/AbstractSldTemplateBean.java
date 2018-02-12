package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
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
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSldTemplateBean <L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									SCALE extends GeoJsfScale<L,D>, 
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									DS extends GeoJsfDataSource<L,D,LAYER>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>
									>
		extends AbstractGeoJsfBean<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldTemplateBean.class);

	protected List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	protected SLDTEMPLATE template; public SLDTEMPLATE getTemplate() {return template;} public void setTemplate(SLDTEMPLATE template) {this.template = template;}
	
	public AbstractSldTemplateBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,DS> fbMeta,
									GeoSldFactoryBuilder<L,D> fbSld
			, final Class<DS> cDs, final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cSldType, final Class<SLD> cSld)
	{
		super(fbCore,fbMeta,fbSld,cDs,cTemplate,cSldType,cSld);
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