package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.PropertyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyNameFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyNameFactory.class);
	public static final long serialVersionUID=1;
	
	public static PropertyName build(String value)
	{
		PropertyName xml = new PropertyName();
		xml.setValue(value);
		return xml;
	}
}