package net.sf.geojsf.util.factory.xml.geojsf;

import java.io.Serializable;


import org.geojsf.xml.geojsf.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoordinateFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCoordinateFactory.class);
	
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	public static Coordinate build(double lat, double lon)
	{
		Coordinate xml = new Coordinate();
		
		xml.setLat(lat);
		xml.setLon(lon);
		
		return xml;
	}
}
