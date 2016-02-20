package org.geojsf.mbean.handler;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.geojsf.factory.ejb.sld.EjbGeoSldRuleFactory;
import org.geojsf.factory.txt.TxtSldRuleFactory;
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
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class SldRuleHandler <L extends UtilsLang,
							D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SldRuleHandler.class);
	
	private GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDTEMPLATE> fGeo;
	
	final String[] defaultLangs;
	
	final Class<SLD> cSld;
	final Class<RULE> cRule;
	final Class<G> cGraphic;
	final Class<GT> cGraphicType;
	final Class<GS> cGraphicStyle;
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	
	private EjbGeoSldRuleFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE> efRule;
	private TxtSldRuleFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE> tfRule;
	
	public SldRuleHandler(GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDTEMPLATE> fGeo,
			final String[] defaultLangs,
			final Class<L> cL,
			final Class<D> cD,
			final Class<RULE> cRule,
			final Class<SLD> cSld,
			final Class<G> cGraphic,
			final Class<GT> cGraphicType,
			final Class<GS> cGraphicStyle)
	{
		this.fGeo=fGeo;
		this.defaultLangs=defaultLangs;
		
		this.cRule=cRule;
		this.cSld=cSld;
		this.cGraphic=cGraphic;
		this.cGraphicType=cGraphicType;
		this.cGraphicStyle=cGraphicStyle;
		
		mapBounds = new Hashtable<RULE,String>();
		
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
		
		efRule = EjbGeoSldRuleFactory.factory(cRule);
		tfRule = TxtSldRuleFactory.factory();
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					G extends UtilsGraphic<L,D,G,GT,GS>,
					GT extends UtilsStatus<GT,L,D>,
					GS extends UtilsStatus<GS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDTEMPLATE>,
					SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>> 
		SldRuleHandler<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDTEMPLATE> factory(GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDTEMPLATE> fGeo,final String[] defaultLangs,final Class<L> cL, final Class<D> cD,final Class<RULE> cRule,final Class<SLD> cSld,final Class<G> cGraphic,final Class<GT> cGraphicType,final Class<GS> cGraphicStyle)
	{
		return new SldRuleHandler<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDTEMPLATE>(fGeo,defaultLangs,cL,cD,cRule,cSld,cGraphic,cGraphicType,cGraphicStyle);
	}
	
	//SLD
	private SLD sld; public SLD getSld(){return sld;} public void setSld(SLD sld){this.sld = sld;}
	
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
		if(rule.getGraphic()!=null)
		{
			rule.getGraphic().setType(fGeo.find(cGraphicType, rule.getGraphic().getType()));
			if(rule.getGraphic().getStyle()!=null)
			{
				rule.getGraphic().setStyle(fGeo.find(cGraphicStyle, rule.getGraphic().getStyle()));
			}
			
		}
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
		rule = fGeo.load(cRule, rule);
		logger.info(AbstractLogMessage.selectEntity(rule,rule.getGraphic()));
		rule = efLang.persistMissingLangs(fGeo, defaultLangs, rule);
		rule = efDescription.persistMissingLangs(fGeo, defaultLangs, rule);
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