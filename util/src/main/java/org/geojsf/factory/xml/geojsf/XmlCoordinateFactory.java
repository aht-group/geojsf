package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;


import org.geojsf.xml.geojsf.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoordinateFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCoordinateFactory.class);
	public static final long serialVersionUID=1;
	

	public static Coordinate build(double lat, double lon)
	{
		Coordinate xml = new Coordinate();
		
		
		xml.setLon(lon);
		xml.setLat(lat);
		
		return xml;
	}
	
	public static Coordinate build()
	{
		Coordinate xml = new Coordinate();
		return xml;
	}
}
