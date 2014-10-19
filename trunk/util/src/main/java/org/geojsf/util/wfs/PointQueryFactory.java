package org.geojsf.util.wfs;

import org.geojsf.xml.gml.Coordinates;
import org.geojsf.xml.gml.Point;
import org.geojsf.xml.ogc.DWithin;
import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.ogc.Filter;
import org.geojsf.xml.ogc.PropertyName;
import org.geojsf.xml.wfs.GetFeature;
import org.geojsf.xml.wfs.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointQueryFactory
{	
	final static Logger logger = LoggerFactory.getLogger(PointQueryFactory.class);
	
	private Coordinates coordinates;
	private Distance distance;
	
	public void setDistance(Distance distance) {this.distance = distance;}
	public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}

	public PointQueryFactory()
	{
		
	}
	
	public static GetFeature cGetFeature(String typeName, String[] properties, String geometryColumn, Coordinates coordinates, Distance distance)
	{
		PointQueryFactory pwf = new PointQueryFactory();
		pwf.setCoordinates(coordinates);
		pwf.setDistance(distance);
		return pwf.createGetFeature(typeName, properties, geometryColumn);
	}
	
	private GetFeature createGetFeature(String typeName, String[] properties, String geometryColumn)
	{
		GetFeature gFeature = new GetFeature();
		gFeature.setService("WFS");
		gFeature.setVersion("1.0.0");
		gFeature.setOutputFormat("GML2");
		
		gFeature.setQuery(createQuery(typeName,properties,geometryColumn));
		
		return gFeature;
	}

	private Query createQuery(String typeName, String[] properties, String geometryColumn)
	{
		Query q = new Query();
		q.setTypeName(typeName);
		
		for(String s : properties)
		{
			PropertyName pn = new PropertyName();
			pn.setValue(s.toLowerCase());
			q.getPropertyName().add(pn);
		}
		
		q.setFilter(createFilter(geometryColumn));
		
		return q;
	}
	
	private Filter createFilter(String geometryColumn)
	{
		Filter filter = new Filter();
		filter.setDWithin(createDWithin(distance,geometryColumn));
		return filter;
	}
	
	private DWithin createDWithin(Distance distance, String geometryColumn)
	{
		DWithin dw = new DWithin();
		
		PropertyName pn = new PropertyName();
		pn.setValue(geometryColumn);
		dw.setPropertyName(pn);
		
		dw.setPoint(createPoint());
		dw.setDistance(checkDistanceUnit(distance));
		
		return dw;
	}
	
	private Point createPoint()
	{
		Point point = new Point();
		
		point.setSrsName("http://www.opengis.net/gml/srs/epsg.xml#4326");
		point.setCoordinates(coordinates);
		
		return point;
	}
	
	private Distance checkDistanceUnit(Distance distance)
	{
		if(distance.getUnits().equals("degree")){return distance;}
		if(distance.getUnits().equals("meter"))
		{
			logger.debug("Converting to degree");
			Double m = new Double(distance.getValue());
			Double latTraveledDeg = (1 / 110.54) * (m/1000);
			Distance result = new Distance();
			result.setUnits("degree");
			result.setValue(latTraveledDeg.toString());
			return result;
		}
		else
		{
			logger.warn("Unkniwn unit: "+distance.getUnits());
			return null;
		}
	}
}
