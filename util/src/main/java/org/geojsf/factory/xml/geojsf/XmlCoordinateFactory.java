package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Point;

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
	
	public static Coordinate build(Point point)
	{
		Coordinate xml = build();
		xml.setLat(point.getCoordinate().y);
		xml.setLon(point.getCoordinate().x);
		return xml;
	}
	
	public static Coordinate build(org.locationtech.jts.geom.Point point)
	{
		Coordinate xml = build();
		xml.setLat(point.getCoordinate().y);
		xml.setLon(point.getCoordinate().x);
		return xml;
	}
}
