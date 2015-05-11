package org.geojsf.factory.wkt;

import org.geojsf.xml.geojsf.Wkt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class PointFactory
{
	final static Logger logger = LoggerFactory.getLogger(PointFactory.class);
	
	private GeometryFactory gf;
	private WKTReader wktReader;
	
	public PointFactory()
	{
		PrecisionModel pm = new PrecisionModel(PrecisionModel.FLOATING);
		gf = new GeometryFactory(pm,4326);
		
		wktReader = new WKTReader();
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
    
    public Point build(Wkt wkt) throws ParseException
    {
		return build(wkt.getValue());
    }
    
    public Point build(String wkt) throws ParseException
    {
		Geometry geometry = wktReader.read(wkt);
		geometry.setSRID(4326);
		return (Point)geometry;
    }
}