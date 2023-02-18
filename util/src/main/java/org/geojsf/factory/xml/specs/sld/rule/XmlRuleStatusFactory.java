package org.geojsf.factory.xml.specs.sld.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.factory.xml.specs.sld.XmlGraphicFactory;
import org.geojsf.factory.xml.specs.sld.XmlPointSymbolizerFactory;
import org.geojsf.factory.xml.specs.sld.XmlSizeFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.Rule;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleStatusFactory <L extends JeeslLang,
								SLD extends GeoJsfSld<L,?,?,?,RULE,?,?>,
								RULE extends GeoJsfSldRule<L,?,?>> 
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleStatusFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider sldCp;
	
	public XmlRuleStatusFactory(final SldConfigurationProvider sldCp)
	{
		this.sldCp=sldCp;
	}
	
	@SuppressWarnings("unchecked")
	public <X extends JeeslStatus<L,?,X>> List<Rule> build(SLD sld)
	{
		List<Rule> rules = new ArrayList<Rule>();
		try
		{
			if(sldCp.getJeeslFacade()==null){throw new UtilsConfigurationException(JeeslFacade.class.getSimpleName()+" not available in "+sldCp.getClass().getSimpleName());}
			if(sld.getStatusClass()==null){throw new UtilsConfigurationException("Attribute statusClass not available in "+sld.getClass().getSimpleName());}
			if(sld.getStatusClass()==null){throw new UtilsConfigurationException("Attribute statusAttribute not available in "+sld.getClass().getSimpleName());}
			Class<X> cStatus = (Class<X>)Class.forName(sld.getStatusClass()).asSubclass(JeeslStatus.class);
			for(X x :sldCp.getJeeslFacade().allOrderedPositionVisible(cStatus))
			{
				rules.add(build(sld,x));
			}
		}
		catch (ClassNotFoundException e){logger.error(e.getMessage());}
		catch (UtilsConfigurationException e) {logger.error(e.getMessage());}
		return rules;
	}
	
	private <X extends JeeslStatus<L,?,X>> Rule build(SLD sld, X x)
	{
		Rule xml = XmlRuleFactory.build(x.getName().get(sldCp.getLocaleCode()).getLang());
		xml.setFilter(XmlFilterFactory.equal(sld.getStatusAttribute(), x.getId()));
		xml.setPointSymbolizer(XmlPointSymbolizerFactory.build());
		xml.getPointSymbolizer().setGraphic(XmlGraphicFactory.external(sldCp.getSymbolizerUrlPrefix()+"12/"+x.getId()));
		xml.getPointSymbolizer().getGraphic().setSize(XmlSizeFactory.build(12));
		return xml;
	}

}