package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.Literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLiteralFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLiteralFactory.class);
	public static final long serialVersionUID=1;
	
	public static Literal build(String value)
	{
		Literal xml = new Literal();
		xml.setValue(value);
		return xml;
	}
}