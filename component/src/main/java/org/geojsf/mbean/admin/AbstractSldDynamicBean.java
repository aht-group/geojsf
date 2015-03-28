package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSldDynamicBean <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLD extends GeoJsfSld<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldDynamicBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoSldFactory<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE> efSld;
	
	protected GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDTEMPLATE> fGeo;
	
	private String[] langKeys;
	private Class<SLD> cSld;
	private Class<SLDTEMPLATE> cTemplate;
	
	public void initSuper(String[] langKeys,final Class<L> cLang, final Class<D> clDescription,final Class<SLD> cSld, final Class<SLDTYPE> cType,final Class<SLDTEMPLATE> cTemplate)
	{
		this.langKeys=langKeys;
		this.cSld=cSld;
		this.cTemplate=cTemplate;
		
		efLang = EjbLangFactory.createFactory(cLang);
		efDescription = EjbDescriptionFactory.createFactory(clDescription);
		efSld = EjbGeoSldFactory.factory(cSld);
		
		try {type = fGeo.fByCode(cType, GeoJsfSldType.Code.status.toString());}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		templates = fGeo.all(cTemplate);
	}
	
	//Type
	private SLDTYPE type;
	public SLDTYPE getType() {return type;}
	
	//TEMPLATES
	protected List<SLDTEMPLATE> templates;
	public List<SLDTEMPLATE> getTemplates(){return templates;}

	//SLDs
	protected List<SLD> slds;
	public List<SLD> getSlds() {return slds;}
	public void setSlds(List<SLD> slds) {this.slds = slds;}
	
	protected void reloadSlds()
	{
		slds = fGeo.allForParent(cSld,"type",type);
	}
	
	//TEMPLATE
	protected SLD sld;
	public SLD getSld() {return sld;}
	public void setSld(SLD sld) {this.sld = sld;}
	
	public void selectSld() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(sld));
	}
	
	public void addSld()
	{
		logger.info(AbstractLogMessage.addEntity(cSld));
		sld = efSld.build(null,type);
		try
		{
			sld.setName(efLang.createEmpty(langKeys));
			sld.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (UtilsContraintViolationException e) {e.printStackTrace();}
	}
	
	public void cancelSld()
	{
		sld=null;
	}
	
	public void saveSld() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld.setTemplate(fGeo.find(cTemplate, sld.getTemplate()));
		sld = fGeo.save(sld);
		reloadSlds();
	}
	
	public void rmSld() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
	}
}