package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlNameFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlNameFactory.class);
	public static final long serialVersionUID=1;
	
	public static Name build(String value)
	{
		Name xml = new Name();
		xml.setValue(value);
		return xml;
	}
}