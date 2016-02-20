package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.WellKnownName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWellKnownNameFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlWellKnownNameFactory.class);
	public static final long serialVersionUID=1;
	
	public static WellKnownName build(String value)
	{
		WellKnownName xml = new WellKnownName();
		xml.setValue(value);
		return xml;
	}
}