package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.ogc.XmlFunctionFactory;
import org.geojsf.model.xml.specs.sld.CssParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCssParameterFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCssParameterFactory.class);
	public static final long serialVersionUID=1;
	
	public static CssParameter function(String name, String property, String value)
	{
		CssParameter xml = new CssParameter();
		xml.setName(name);
		xml.getContent().add(XmlFunctionFactory.env(property, value));
		return xml;
	}
	public static CssParameter value(String name, String value)
	{
		CssParameter xml = new CssParameter();
		xml.setName(name);
		xml.getContent().add(value);
		return xml;
	}
}