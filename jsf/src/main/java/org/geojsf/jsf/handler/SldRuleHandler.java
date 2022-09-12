package org.geojsf.jsf.handler;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldRuleFactory;
import org.geojsf.factory.txt.TxtSldRuleFactory;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.io.graphic.EjbGraphicFactory;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class SldRuleHandler <L extends JeeslLang, D extends JeeslDescription,
							G extends JeeslGraphic<GT,F,GS>, GT extends JeeslGraphicType<L,D,GT,G>,
							F extends JeeslGraphicComponent<G,F,GS>, GS extends JeeslGraphicShape<L,D,GS,G>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
							SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,G>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SldRuleHandler.class);
	
	private final GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld;
	
	private final GeoSldFactoryBuilder<L,D,?,?,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld;
	private final SvgFactoryBuilder<L,D,G,GT,F,GS> fbSvg;
	
	final String[] defaultLangs;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbGraphicFactory<L,D,G,GT,GS> efGraphic;
	private EjbGeoSldRuleFactory<SLD,RULE> efRule;
	
	private TxtSldRuleFactory<RULE> tfRule;
	
	private Map<RULE,String> mapBounds; public Map<RULE,String> getMapBounds() {return mapBounds;}
	
	private SLD sld; public SLD getSld(){return sld;} public void setSld(SLD sld){this.sld = sld;}
	private RULE rule; public RULE getRule(){return rule;} public void setRule(RULE rule){this.rule = rule;}
	private String lowerBound; public String getLowerBound() {return lowerBound;} public void setLowerBound(String lowerBound) {this.lowerBound = lowerBound;}
	private String upperBound; public String getUpperBound() {return upperBound;} public void setUpperBound(String upperBound) {this.upperBound = upperBound;}
	
	public SldRuleHandler(GeoSldFactoryBuilder<L,D,?,?,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld,
						SvgFactoryBuilder<L,D,G,GT,F,GS> fbSvg,
						GeoSldFacade<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> fSld,
			final String[] defaultLangs

)
	{
		this.fbSld=fbSld;
		this.fbSvg=fbSvg;
		
		this.fSld=fSld;
		this.defaultLangs=defaultLangs;
		
		mapBounds = new Hashtable<RULE,String>();
	
		efLang = EjbLangFactory.instance(fbSld.getClassL());
		efDescription = EjbDescriptionFactory.factory(fbSld.getClassD());
		efGraphic = fbSvg.efGraphic();
		efRule = fbSld.ejbRule();
		tfRule = TxtSldRuleFactory.factory();
	}
	
	public void reloadSld()
	{
		sld = fSld.load(sld);
		logger.trace("Rules: "+sld.getRules().size());
		
		mapBounds.clear();
		for(RULE r : sld.getRules())
		{
			mapBounds.put(r, tfRule.build(r));
		}
	}
	
	public void onRowReorder(ReorderEvent event) throws JeeslConstraintViolationException, JeeslLockingException
	{
        logger.trace( "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        PositionListReorderer.reorder(fSld, sld.getRules());
        reloadSld();
    }
	
	public void addRule()
	{
		logger.info(AbstractLogMessage.addEntity(fbSld.getClassRule()));
		rule = efRule.build(sld);
		rule.setName(efLang.createEmpty(defaultLangs));
		rule.setDescription(efDescription.createEmpty(defaultLangs));
		
		GT type = fSld.fByEnum(fbSvg.getClassGraphicType(), JeeslGraphicType.Code.symbol);
		GS style = fSld.fByEnum(fbSvg.getClassFigureStyle(), JeeslGraphicShape.Code.shapeCircle);
		G g = efGraphic.buildSymbol(type, style);
		rule.setGraphic(g);
	}
	
	private void reloadRule()
	{
		rule = fSld.load(fbSld.getClassRule(), rule);
	}
	
	public void selectRule()
	{
		reloadRule();
		logger.info(AbstractLogMessage.selectEntity(rule,rule.getGraphic()));
		rule = efLang.persistMissingLangs(fSld, defaultLangs, rule);
		rule = efDescription.persistMissingLangs(fSld, defaultLangs, rule);
		rule2String();
	}
	
	public void cancelRule()
	{
		rule=null;
	}
	
	public void saveRule() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(rule));
		if(rule.getGraphic()!=null)
		{
			efGraphic.converter(fSld,rule.getGraphic());
		}
		string2Rule();
		rule = fSld.save(fbSld.getClassSld(),sld,rule);
		reloadRule();
		reloadSld();
		rule2String();
	}
	
	public void rmRule() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(rule));
		fSld.delete(fbSld.getClassSld(),sld,rule);
		rule = null;
		reloadSld();
	}

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
}