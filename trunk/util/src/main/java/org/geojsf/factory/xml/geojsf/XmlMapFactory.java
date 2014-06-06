package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.xml.geojsf.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static Map build(double lat, double lon)
	{
		Map xml = new Map();
		xml.setLat(lat);
		xml.setLon(lon);
		return xml;
	}
}
