package org.geojsf.factory.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

public class GeoJsonGeometryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsonGeometryFactory.class);
	public static final long serialVersionUID=1;
	
	public static GeoJsonObject build(MultiPolygon polygon)
	{
		org.geojson.Polygon json = new org.geojson.Polygon();
		
//		Geometry p = TopologyPreservingSimplifier.simplify(polygon, 100000000);
//		logger.info(p.getGeometryType());
		
		List<LngLatAlt> list = new ArrayList<LngLatAlt>();
		for(Coordinate c : polygon.getCoordinates())
		{
			list.add(GeoJsonCoordinateFactory.build(c));
		}
		
		json.add(list);
		
		return json;
	}
	
	public static GeoJsonObject build(Point point)
	{
		org.geojson.Point json = new org.geojson.Point();
		
		json.setCoordinates(GeoJsonCoordinateFactory.build(point));
		
		return json;
	}
}