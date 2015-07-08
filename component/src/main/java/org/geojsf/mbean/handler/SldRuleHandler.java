package org.geojsf.mbean.handler;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.geojsf.factory.ejb.sld.EjbGeoSldRuleFactory;
import org.geojsf.factory.txt.TxtSldRuleFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SldRuleHandler <L extends UtilsLang,
							D extends UtilsDescription,
							CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
							SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SldRuleHandler.class);
	
	private GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
	
	final String[] defaultLangs;
	
	final Class<SLD> cSld;
	final Class<RULE> cRule;
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	
	private EjbGeoSldRuleFactory<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE> efRule;
	private TxtSldRuleFactory<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE> tfRule;
	
	public SldRuleHandler(GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo,final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<RULE> cRule,final Class<SLD> cSld)
	{
		this.fGeo=fGeo;
		this.defaultLangs=defaultLangs;
		
		this.cRule=cRule;
		this.cSld=cSld;
		
		mapBounds = new Hashtable<RULE,String>();
		
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
		
		efRule = EjbGeoSldRuleFactory.factory(cRule);
		tfRule = TxtSldRuleFactory.factory();
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>> 
		SldRuleHandler<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> factory(GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo,final String[] defaultLangs,final Class<L> cL, final Class<D> cD,final Class<RULE> cRule,final Class<SLD> cSld)
	{
		return new SldRuleHandler<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(fGeo,defaultLangs,cL,cD,cRule,cSld);
	}
	
	//SLD
	private SLD sld;
	public SLD getSld(){return sld;}
	public void setSld(SLD sld){this.sld = sld;}
	
	public void reloadSld()
	{
		sld = fGeo.load(cSld,sld);
		logger.trace("Rules: "+sld.getRules().size());
		
		mapBounds.clear();
		for(RULE r : sld.getRules())
		{
			mapBounds.put(r, tfRule.build(r));
		}
	}
	
	public void onRowReorder(ReorderEvent event) throws UtilsConstraintViolationException, UtilsLockingException
	{
        logger.trace( "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        int i=1;
        for(RULE rule : sld.getRules())
        {
        	rule.setPosition(i);i++;
        }
        sld = fGeo.save(sld);
        reloadSld();
    }
	
	//Rule
	private RULE rule;
	public RULE getRule(){return rule;}
	public void setRule(RULE rule){this.rule = rule;}
	
	public void addRule()
	{
		logger.info(AbstractLogMessage.addEntity(cRule));
		rule = efRule.build(sld);
		rule.setName(efLang.createEmpty(defaultLangs));
		rule.setDescription(efDescription.createEmpty(defaultLangs));
	}
	
	public void saveRule() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(rule));
		string2Rule();
		rule = fGeo.save(rule);
		reloadSld();
		rule2String();
	}
	
	public void rmRule() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(rule));
		fGeo.rm(rule);
		rule = null;
		reloadSld();
	}
	
	public void selectRule()
	{
		logger.info(AbstractLogMessage.selectEntity(rule));
		rule = efLang.persistMissingLangs(fGeo, defaultLangs, rule);
		rule = efDescription.persistMissingLangs(fGeo, defaultLangs, rule);
		logger.info(rule.getName().size()+" "+rule.getDescription().size());
		rule2String();
	}
	
	public void cancelRule()
	{
		rule=null;
	}
	
	//Bounds
	private Map<RULE,String> mapBounds;
	public Map<RULE, String> getMapBounds() {return mapBounds;}
	
	private void rule2String()
	{
		lowerBound = null; if(rule.getLowerBound()!=null){lowerBound = ""+rule.getLowerBound();}
		upperBound = null; if(rule.getUpperBound()!=null){upperBound = ""+rule.getUpperBound();}
	}
	
	private void string2Rule()
	{
		rule.setLowerBound(null);
		rule.setUpperBound(null);
		
		try
		{
			if(lowerBound!=null && lowerBound.length()>0){rule.setLowerBound(new Double(lowerBound));}
			if(upperBound!=null && upperBound.length()>0){rule.setUpperBound(new Double(upperBound));}
		}
		catch (NumberFormatException e){}
	}
	
	private String lowerBound;
	public String getLowerBound() {return lowerBound;}
	public void setLowerBound(String lowerBound) {this.lowerBound = lowerBound;}

	private String upperBound;
	public String getUpperBound() {return upperBound;}
	public void setUpperBound(String upperBound) {this.upperBound = upperBound;}
}