package org.geojsf.factory.json;

import java.io.Serializable;

import org.geojson.LngLatAlt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;

public class GeoJsonCoordinateFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsonCoordinateFactory.class);
	public static final long serialVersionUID=1;
	
	public static LngLatAlt build(Coordinate coordintate)
	{
		LngLatAlt json = new LngLatAlt();
		
		json.setLatitude(coordintate.y);
		json.setLongitude(coordintate.x);
		
		return json;
	}
	
	public static LngLatAlt build(org.locationtech.jts.geom.Coordinate coordintate)
	{
		LngLatAlt json = new LngLatAlt();
		
		json.setLatitude(coordintate.y);
		json.setLongitude(coordintate.x);
		
		return json;
	}
	
	public static LngLatAlt build(com.vividsolutions.jts.geom.Point point)
	{
		LngLatAlt json = new LngLatAlt();
		
		json.setLatitude(point.getY());
		json.setLongitude(point.getX());
		
		return json;
	}
}
