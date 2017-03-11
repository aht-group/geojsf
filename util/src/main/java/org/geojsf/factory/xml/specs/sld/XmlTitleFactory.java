package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTitleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlTitleFactory.class);
	public static final long serialVersionUID=1;
	
	public static Title build(String value)
	{
		Title xml = new Title();
		xml.setValue(value);
		return xml;
	}
}