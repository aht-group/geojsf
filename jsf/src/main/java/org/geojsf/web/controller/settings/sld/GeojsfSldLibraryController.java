package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfProvideSldStatus;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.web.AbstractJeeslWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfSldLibraryController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,?>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfSldLibraryController.class);
	
	private GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld;
	
	protected final GeoSldFactoryBuilder<L,D,?,?,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld;

	protected final EjbGeoSldFactory<SLDTYPE,SLD> efSld;
	
	
	private List<SLDTYPE> types; public List<SLDTYPE> getTypes() {return types;}
	private List<SLDTEMPLATE> templates; public List<SLDTEMPLATE> getTemplates(){return templates;}
	private List<SLD> slds; public List<SLD> getSlds() {return slds;} public void setSlds(List<SLD> slds) {this.slds = slds;}
	private List<String> sldStatusClasses; public List<String> getSldStatusClasses() {return sldStatusClasses;}
	
	private SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}

	public GeojsfSldLibraryController(GeoSldFactoryBuilder<L,D,?,?,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.fbSld=fbSld;

		efSld = fbSld.ejbSld();
		
		sldStatusClasses = new ArrayList<String>();
	}
	
	public void postConstructSldLibrary(GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld,
											JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage)
	{
		super.postConstructWebController(lp,bMessage);
		this.fSld=fSld;
		templates = fSld.all(fbSld.getClassTemplate());
		types = fSld.allOrderedPositionVisible(fbSld.getClassSldType());
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
		sld.setName(efLang.createEmpty(lp.getLocales()));
		sld.setDescription(efDescription.createEmpty(lp.getLocales()));
	}
	
	public void saveSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("1: "+sld.getStatusAttribute());
		sld.setType(fSld.find(fbSld.getClassSldType(),sld.getType()));
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.template.toString())){sld.setTemplate(null);}
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.status.toString())){sld.setStatusClass(null);}
//		
		if(sld.getTemplate()!=null){sld.setTemplate(fSld.find(fbSld.getClassTemplate(),sld.getTemplate()));}
		
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld = fSld.save(sld);
		logger.info("2: "+sld.getStatusAttribute());
		reloadSlds();
	}
	
	public void deleteSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
		fSld.rm(sld);
		reloadSlds();
		reset(true);
	}
	
	public void changeTYPE()
	{
		sld.setType(fSld.find(fbSld.getClassSldType(), sld.getType()));
		logger.info(AbstractLogMessage.selectOneMenuChange(sld.getType()));
	}
	
	public <C extends GeoJsfProvideSldStatus> void activateSldType(Class<C> c)
	{
		sldStatusClasses.add(c.getName());
	}
}