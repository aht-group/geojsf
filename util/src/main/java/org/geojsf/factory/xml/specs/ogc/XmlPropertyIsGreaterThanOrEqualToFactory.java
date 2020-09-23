package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.PropertyIsGreaterThanOrEqualTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyIsGreaterThanOrEqualToFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyIsGreaterThanOrEqualToFactory.class);
	public static final long serialVersionUID=1;

	public static PropertyIsGreaterThanOrEqualTo build(String property, String attribute)
	{
		PropertyIsGreaterThanOrEqualTo xml = new PropertyIsGreaterThanOrEqualTo();
		xml.setPropertyName(XmlPropertyNameFactory.build(property));
		xml.setLiteral(XmlLiteralFactory.build(attribute));
		return xml;
	}
}