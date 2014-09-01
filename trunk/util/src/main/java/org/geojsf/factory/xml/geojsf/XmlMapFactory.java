package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static Map build(ViewPort viewport)
	{
		Map xml = new Map();
		xml.setViewPort(viewport);
		return xml;
	}
}
