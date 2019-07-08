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
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFactory;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class SldRuleHandler <L extends UtilsLang, D extends UtilsDescription,
							G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
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
							RULE extends GeoJsfSldRule<L,D,G>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SldRuleHandler.class);
	
	private GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
	
	final String[] defaultLangs;
	
	final Class<SLD> cSld;
	final Class<RULE> cRule;
	final Class<G> cGraphic;
	final Class<GT> cGraphicType;
	final Class<FS> cGraphicStyle;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbGraphicFactory<L,D,G,GT,F,FS> efGraphic;
	
	private EjbGeoSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efRule;
	private TxtSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> tfRule;
	
	private Map<RULE,String> mapBounds; public Map<RULE,String> getMapBounds() {return mapBounds;}
	
	private SLD sld; public SLD getSld(){return sld;} public void setSld(SLD sld){this.sld = sld;}
	private RULE rule; public RULE getRule(){return rule;} public void setRule(RULE rule){this.rule = rule;}
	private String lowerBound; public String getLowerBound() {return lowerBound;} public void setLowerBound(String lowerBound) {this.lowerBound = lowerBound;}
	private String upperBound; public String getUpperBound() {return upperBound;} public void setUpperBound(String upperBound) {this.upperBound = upperBound;}
	
	public SldRuleHandler(GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo,
			final String[] defaultLangs,
			final Class<L> cL,
			final Class<D> cD,
			final Class<RULE> cRule,
			final Class<SLD> cSld,
			final Class<G> cGraphic,
			final Class<GT> cGraphicType,
			final Class<F> cF,
			final Class<FS> cGraphicStyle)
	{
		this.fGeo=fGeo;
		this.defaultLangs=defaultLangs;
		
		this.cRule=cRule;
		this.cSld=cSld;
		this.cGraphic=cGraphic;
		this.cGraphicType=cGraphicType;
		this.cGraphicStyle=cGraphicStyle;
		
		mapBounds = new Hashtable<RULE,String>();
		
		SvgFactoryBuilder<L,D,G,GT,F,FS> ffSvg = SvgFactoryBuilder.factory(cL,cD,cGraphic,cGraphicType,cF,cGraphicStyle);
		
		efLang = EjbLangFactory.factory(cL);
		efDescription = EjbDescriptionFactory.factory(cD);
		efGraphic = ffSvg.efGraphic();
		efRule = EjbGeoSldRuleFactory.factory(cRule);
		tfRule = TxtSldRuleFactory.factory();
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>> 
		SldRuleHandler<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> factory(GeoJsfFacade<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo,final String[] defaultLangs,final Class<L> cL, final Class<D> cD,final Class<RULE> cRule,final Class<SLD> cSld,final Class<G> cGraphic,final Class<GT> cGraphicType,final Class<F> cF,final Class<FS> cGraphicStyle)
	{
		return new SldRuleHandler<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fGeo,defaultLangs,cL,cD,cRule,cSld,cGraphic,cGraphicType,cF,cGraphicStyle);
	}
	
	public void reloadSld()
	{
		sld = fGeo.load(sld);
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
        PositionListReorderer.reorder(fGeo, sld.getRules());
        reloadSld();
    }
	
	
	public void addRule() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cRule));
		rule = efRule.build(sld);
		rule.setName(efLang.createEmpty(defaultLangs));
		rule.setDescription(efDescription.createEmpty(defaultLangs));
		
		GT type = fGeo.fByCode(cGraphicType, JeeslGraphicType.Code.symbol.toString());
		FS style = fGeo.fByCode(cGraphicStyle, JeeslGraphicStyle.Code.circle.toString());
		G g = efGraphic.buildSymbol(type, style);
		rule.setGraphic(g);
	}
	
	private void reloadRule()
	{
		rule = fGeo.load(cRule, rule);
	}
	
	public void selectRule()
	{
		reloadRule();
		logger.info(AbstractLogMessage.selectEntity(rule,rule.getGraphic()));
		rule = efLang.persistMissingLangs(fGeo, defaultLangs, rule);
		rule = efDescription.persistMissingLangs(fGeo, defaultLangs, rule);
		rule2String();
	}
	
	public void cancelRule()
	{
		rule=null;
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
		rule = fGeo.save(cSld,sld,rule);
		reloadRule();
		reloadSld();
		rule2String();
	}
	
	public void rmRule() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(rule));
		fGeo.rm(cSld,sld,rule);
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