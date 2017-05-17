package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfProvideSldStatus;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSldLibraryBean <L extends UtilsLang, D extends UtilsDescription,
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
									RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
		extends AbstractGeoJsfBean<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldLibraryBean.class);
	
	private List<SLDTYPE> types; public List<SLDTYPE> getTypes() {return types;}
	private List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	private List<SLD> slds; public List<SLD> getSlds() {return slds;} public void setSlds(List<SLD> slds) {this.slds = slds;}
	private List<String> sldStatusClasses; public List<String> getSldStatusClasses() {return sldStatusClasses;}
	
	private SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}

	public AbstractSldLibraryBean(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<SCALE> cScale,final Class<VIEW> cView, final Class<VP> cViewPort,final Class<DS> cDs, final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cSldType, final Class<SLD> cSld)
	{
		super(cL,cD,cCategory,cService,cLayer,cMap,cScale,cView,cViewPort,cDs,cTemplate,cSldType,cSld);
		
		sldStatusClasses = new ArrayList<String>();
	}
	
	protected void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo)
	{
		super.initSuper(langKeys,fGeo);
	
		templates = fGeo.all(cTemplate);
		types = fGeo.allOrderedPositionVisible(cSldType);
	}
	
	public void cancelSld(){reset(true);}
	private void reset(boolean rSld)
	{
		if(rSld){sld=null;}
	}

	protected void reloadSlds()
	{
		slds = fGeo.fLibrarySlds();
	}

	public void selectSld()
	{
		logger.info(AbstractLogMessage.selectEntity(sld));
		sld = fGeo.load(sld);
	}
	
	public void addSld()
	{
		logger.info(AbstractLogMessage.addEntity(cSld));
		sld = efSld.build(null,true);
		sld.setName(efLang.createEmpty(langKeys));
		sld.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void saveSld() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("1: "+sld.getStatusAttribute());
		sld.setType(fGeo.find(cSldType,sld.getType()));
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.template.toString())){sld.setTemplate(null);}
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.status.toString())){sld.setStatusClass(null);}
//		
		if(sld.getTemplate()!=null){sld.setTemplate(fGeo.find(cTemplate,sld.getTemplate()));}
		
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld = fGeo.save(sld);
		logger.info("2: "+sld.getStatusAttribute());
		reloadSlds();
	}
	
	public void deleteSld() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
		fGeo.rm(sld);
		reloadSlds();
		reset(true);
	}
	
	public void changeTYPE()
	{
		sld.setType(fGeo.find(cSldType, sld.getType()));
		logger.info(AbstractLogMessage.selectOneMenuChange(sld.getType()));
	}
	
	protected <C extends GeoJsfProvideSldStatus> void activateSldType(Class<C> c)
	{
		sldStatusClasses.add(c.getName());
	}
}