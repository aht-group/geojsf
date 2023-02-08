package org.geojsf.factory.pojo;

import java.io.Serializable;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.geojsf.util.GeoJsfDistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.util.GeometricShapeFactory;

public class JtsSearchCircleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(JtsSearchCircleFactory.class);
	public static final long serialVersionUID=1;
	
	public static Geometry build(Coordinate coordinate, ViewPort viewport)
	{
		GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
		shapeFactory.setNumPoints(32);
		shapeFactory.setCentre(new com.vividsolutions.jts.geom.Coordinate(coordinate.getLon(),coordinate.getLat()));
		shapeFactory.setSize(GeoJsfDistanceCalculator.ltSearchDistance(viewport,10));
		Geometry g = shapeFactory.createCircle();
		g.setSRID(4326);
		return g;
	}

}