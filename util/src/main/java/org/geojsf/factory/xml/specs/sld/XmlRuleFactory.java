package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.factory.xml.specs.ogc.XmlPropertyIsBetweenFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.specs.ogc.Filter;
import org.geojsf.model.xml.specs.sld.PolygonSymbolizer;
import org.geojsf.model.xml.specs.sld.Rule;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleFactory <L extends JeeslLang,
								SLD extends GeoJsfSld<L,?,?,?,SDR,LE,LA>,
								SDR extends GeoJsfSldRule<L,?,?>,
								LE extends JeeslRevisionEntity<L,?,?,?,LA,?>,
								LA extends JeeslRevisionAttribute<L,?,LE,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider scp;
	
	private XmlFilterFactory xfOgcFilter;
	
	public XmlRuleFactory(final SldConfigurationProvider scp)
	{
		this.scp=scp;
		xfOgcFilter = new XmlFilterFactory();
	}
	
	public static Rule build(String name)
	{
		Rule xml = new Rule();
		xml.setName(XmlNameFactory.build(name));
		return xml;
	}
	
	public static Rule build(String name, String title)
	{
		Rule xml = new Rule();
		xml.setName(XmlNameFactory.build(name));
		if(title!=null) {xml.setDescription(XmlDescriptionFactory.title(title));}
		return xml;
	}
	
	
	public List<Rule> build(SLD sld)
	{
		if(sld.getType().getCode().equals(GeoJsfSldType.Type.status.toString())) {return this.buildStatusList(sld);}
		else if(sld.getType().getCode().equals(GeoJsfSldType.Type.rule.toString())) {return this.buildRuleList(sld);}
		return new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	private <X extends JeeslStatus<L,?,X>> List<Rule> buildStatusList(SLD sld)
	{
		List<Rule> rules = new ArrayList<Rule>();
		try
		{
			if(scp.getFacade()==null) {throw new UtilsConfigurationException(JeeslFacade.class.getSimpleName()+" not available in "+scp.getClass().getSimpleName());}
			if(Objects.isNull(sld.getEntity())) {throw new UtilsConfigurationException("No Entity defined in "+sld.getClass().getSimpleName());}
			if(Objects.isNull(sld.getAttribute())) {throw new UtilsConfigurationException("No Attribute defined in "+sld.getClass().getSimpleName());}
			
			Class<X> cStatus = (Class<X>)Class.forName(sld.getAttribute().getEntity().getCode()).asSubclass(JeeslStatus.class);
			for(X x : scp.getFacade().allOrderedPositionVisible(cStatus))
			{
				rules.add(buildStatusElement(sld,x));
			}
		}
		catch (ClassNotFoundException e) {logger.error(e.getMessage());}
		catch (UtilsConfigurationException e) {logger.error(e.getMessage());}
		return rules;
	}
	private <X extends JeeslStatus<L,?,X>> Rule buildStatusElement(SLD sld, X x)
	{
		Rule xml = XmlRuleFactory.build(x.getName().get(scp.getLocaleCode()).getLang());
		xml.setFilter(XmlFilterFactory.equal(sld.getStatusAttribute(), x.getId()));
		xml.setPointSymbolizer(XmlPointSymbolizerFactory.build());
		xml.getPointSymbolizer().setGraphic(XmlGraphicFactory.external(scp.getSymbolizerUrlPrefix()+"/"+sld.getAttribute().getEntity().getCode()+"/12/"+x.getId()));
		xml.getPointSymbolizer().getGraphic().setSize(XmlSizeFactory.build(12));
		return xml;
	}
	
	private List<Rule> buildRuleList(SLD sld)
	{
		List<Rule> rules = new ArrayList<Rule>();
		for(int i=1;i<=5;i++)
		{
			rules.add(buildRuleElement(i,sld));
		}
		return rules;
	}
	private Rule buildRuleElement(int i, SLD sld)
	{
		Filter xFilter = XmlFilterFactory.build();
		xFilter.setPropertyIsBetween(XmlPropertyIsBetweenFactory.build("value", "lb"+i, -9999, "ub"+i,9999));
		
		PolygonSymbolizer xPolygon = XmlPolygonSymbolizerFactory.build();
		xPolygon.setFill(XmlFillFactory.build("c"+i));
		
		Rule xml = XmlRuleFactory.build("Element "+i);
		xml.setFilter(xFilter);
		xml.setPolygonSymbolizer(xPolygon);
		return xml;
	}
}