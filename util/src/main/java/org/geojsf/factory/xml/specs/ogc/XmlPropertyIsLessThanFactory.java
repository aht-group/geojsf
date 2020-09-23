package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.PropertyIsLessThan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyIsLessThanFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyIsLessThanFactory.class);
	public static final long serialVersionUID=1;

	public static PropertyIsLessThan build(String property, String attribute)
	{
		PropertyIsLessThan xml = new PropertyIsLessThan();
		xml.setPropertyName(XmlPropertyNameFactory.build(property));
		xml.setLiteral(XmlLiteralFactory.build(attribute));
		return xml;
	}
}