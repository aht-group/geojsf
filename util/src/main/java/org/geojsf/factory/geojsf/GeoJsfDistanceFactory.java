package org.geojsf.factory.geojsf;

import org.geojsf.model.xml.ogc.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDistanceFactory
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDistanceFactory.class);
	
	public static Distance build(int px, double scale)
	{
		// Scale is correlated with the average size of the scale line (50 pixels)
		double radius = scale / 50 * px;
		Distance distance = new Distance();
		distance.setUnits("meters");
		distance.setValue(radius+"");
		return distance;
	}
}