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
}
