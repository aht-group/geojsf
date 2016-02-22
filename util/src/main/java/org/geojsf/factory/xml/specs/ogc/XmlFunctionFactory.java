package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFunctionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFunctionFactory.class);
	public static final long serialVersionUID=1;
	
	public static Function env(String property, String value)
	{
		Function xml = new Function();
		xml.setName("env");
		xml.getLiteral().add(XmlLiteralFactory.build(property));
		xml.getLiteral().add(XmlLiteralFactory.build(""+value));
		return xml;
	}
}