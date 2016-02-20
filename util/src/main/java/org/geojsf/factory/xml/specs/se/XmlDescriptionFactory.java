package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);
	public static final long serialVersionUID=1;
	
	public static Description title(String value)
	{
		Description xml = new Description();
		xml.setTitle(XmlTitleFactory.build(value));
		return xml;
	}
}