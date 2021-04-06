package org.geojsf.factory.wkt;

import org.geojsf.model.xml.geojsf.Wkt;
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
	
	public Point build(org.geojsf.model.xml.geojsf.Coordinate coordinate)
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
    
    public org.locationtech.jts.geom.Point buildLocationTechPoint(double lat, double lon)
    {
    	org.locationtech.jts.geom.Coordinate coordinate = new org.locationtech.jts.geom.Coordinate();
	coordinate.y = lat;
	coordinate.x = lon;
	org.locationtech.jts.geom.PrecisionModel pm = new org.locationtech.jts.geom.PrecisionModel(org.locationtech.jts.geom.PrecisionModel.FLOATING);

	return new org.locationtech.jts.geom.GeometryFactory(pm,4326).createPoint(coordinate);
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
    
    public org.locationtech.jts.geom.Point buildLocationTechPoint(Wkt wkt) throws ParseException
    {
	return buildLocationTechPoint(wkt.getValue());
    }
    
    public org.locationtech.jts.geom.Point buildLocationTechPoint(String wkt) throws ParseException
    {
	org.locationtech.jts.geom.Geometry geometry = null;
	try {
	    geometry = new org.locationtech.jts.io.WKTReader().read(wkt);
	} catch (org.locationtech.jts.io.ParseException ex) {
	    logger.error("Could not build Point from wkt String: " +ex.getMessage());
	}
	geometry.setSRID(4326);
	return (org.locationtech.jts.geom.Point)geometry;
    }
}