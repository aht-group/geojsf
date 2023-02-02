package org.geojsf.util;

import java.awt.geom.Point2D;
import org.geotools.referencing.GeodeticCalculator;
import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDistanceCalculator {
	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDistanceCalculator.class);
	
	public static double getDistance(Coordinate start, Coordinate end) throws Exception
	{
		final GeodeticCalculator calc = new GeodeticCalculator();
		
		final Point2D c1 = new Point2D.Double(start.x, start.y);
		final Point2D c2 = new Point2D.Double(end.x, end.y );
		calc.setStartingGeographicPoint(c1);
		calc.setDestinationGeographicPoint(c2);
		double distance = calc.getOrthodromicDistance();
		logger.info("Calculating difference between " +c1.toString() +" and " +c2.toString() +" to: " +distance/1000 + " km");
		return distance;
	}
	
}
