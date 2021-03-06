package org.geojsf.util.wfs;

import org.geojsf.model.xml.specs.gml.Coordinates;
import org.geojsf.model.xml.specs.gml.Point;
import org.geojsf.model.xml.specs.ogc.DWithin;
import org.geojsf.model.xml.specs.ogc.Distance;
import org.geojsf.model.xml.specs.ogc.Filter;
import org.geojsf.model.xml.specs.ogc.PropertyName;
import org.geojsf.model.xml.specs.wfs.GetFeature;
import org.geojsf.model.xml.specs.wfs.Query;
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
		dw.setDistance(doNotCheckDistanceUnit(distance));
		
		return dw;
	}
	
	private Point createPoint()
	{
		Point point = new Point();
		
		point.setSrsName("http://www.opengis.net/gml/srs/epsg.xml#4326");
		point.setCoordinates(coordinates);
		
		return point;
	}
	
	private Distance doNotCheckDistanceUnit(Distance distance) {return distance;}
	private Distance checkDistanceUnit(Distance distance)
	{
		// Only the unit 'degree' is verified, so convert ...
		if(distance.getUnits().equals("degree")){return distance;}
		else if(distance.getUnits().equals("kilometers") || distance.getUnits().equals("kilometer"))
		{
			Double km = new Double(distance.getValue());
			return convertToDegree(km);
		}
		else if (distance.getUnits().equals("meters") || distance.getUnits().equals("meter"))
		{
			Double m = new Double(distance.getValue());
			return convertToDegree(m/1000);
		}
		else
		{
			logger.warn("Unknown unit: "+distance.getUnits());
			return null;
		}
	}
	
	private Distance convertToDegree(double km)
	{
		logger.debug("Converting to degree");
		Double degree = (1 / 110.54) * (km);
		
		Distance result = new Distance();
		result.setUnits("degree");
		result.setValue(degree.toString());
		return result;
	}
}
