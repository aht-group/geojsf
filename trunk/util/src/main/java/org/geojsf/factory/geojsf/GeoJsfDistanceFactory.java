package org.geojsf.factory.geojsf;

import org.geojsf.xml.ogc.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDistanceFactory
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDistanceFactory.class);
	
	public static Distance build(int px, double scale)
	{
		double radius = scale * px / 3300;
		
		Distance distance = new Distance();
		distance.setUnits("meter");
		distance.setValue(radius+"");
		return distance;
	}
}