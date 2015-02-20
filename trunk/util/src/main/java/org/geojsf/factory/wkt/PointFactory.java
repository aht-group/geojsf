package org.geojsf.factory.wkt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

public class PointFactory
{
	final static Logger logger = LoggerFactory.getLogger(PointFactory.class);
	
	private GeometryFactory gf;
	
	public PointFactory()
	{
		PrecisionModel pm = new PrecisionModel(PrecisionModel.FLOATING);
		gf = new GeometryFactory(pm,4326);
	}
	
	public Point build(org.geojsf.xml.geojsf.Coordinate coordinate)
	{
		return build(coordinate.getLat(),coordinate.getLon());
	}
	
    public Point build(double lat, double lon)
    {
    	com.vividsolutions.jts.geom.Coordinate coordinate = new com.vividsolutions.jts.geom.Coordinate();
		coordinate.y = lat;
		coordinate.x = lon;
		
		return gf.createPoint(coordinate);
    } 
    
}