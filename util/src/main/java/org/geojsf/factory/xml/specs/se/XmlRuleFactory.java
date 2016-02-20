package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleFactory.class);
	public static final long serialVersionUID=1;
	
	public static Rule build(String name, String title)
	{
		Rule xml = new Rule();
		xml.setName(XmlNameFactory.build(name));
		xml.setDescription(XmlDescriptionFactory.title(title));
		return xml;
	}
}