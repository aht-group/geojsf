package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.ogc.XmlFunctionFactory;
import org.geojsf.model.xml.specs.sld.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSizeFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSizeFactory.class);
	public static final long serialVersionUID=1;
	
	public static Size build(String property, int value)
	{
		Size xml = new Size();
		xml.getContent().add(XmlFunctionFactory.env(property, ""+value));
		return xml;
	}
}