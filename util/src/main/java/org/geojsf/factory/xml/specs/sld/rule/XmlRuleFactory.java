package org.geojsf.factory.xml.specs.sld.rule;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.sld.XmlDescriptionFactory;
import org.geojsf.factory.xml.specs.sld.XmlNameFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.Rule;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleFactory <L extends JeeslLang,D extends JeeslDescription,
								SLD extends GeoJsfSld<L,D,?,?,RULE>,
								RULE extends GeoJsfSldRule<L,D,?>> 
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleFactory.class);
	public static final long serialVersionUID=1;
	
	public XmlRuleFactory(final SldConfigurationProvider sldCp)
	{
		
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
}