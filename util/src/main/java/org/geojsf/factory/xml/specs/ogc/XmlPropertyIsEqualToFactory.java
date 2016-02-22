package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.PropertyIsEqualTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyIsEqualToFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyIsEqualToFactory.class);
	public static final long serialVersionUID=1;
	
	public static PropertyIsEqualTo build(String property, String attribute)
	{
		PropertyIsEqualTo xml = new PropertyIsEqualTo();
		xml.setPropertyName(XmlPropertyNameFactory.build(property));
		xml.setLiteral(XmlLiteralFactory.build(attribute));
		return xml;
	}
}