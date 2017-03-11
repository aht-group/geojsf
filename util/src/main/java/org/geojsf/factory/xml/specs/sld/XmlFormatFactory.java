package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFormatFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFormatFactory.class);
	public static final long serialVersionUID=1;
	
	public static Format svg()
	{
		Format xml = new Format();
		xml.setValue("image/svg+xml");
		return xml;
	}
}