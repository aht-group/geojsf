package net.sf.geojsf.util.factory;

import java.util.List;

import org.geojsf.xml.gml.Coordinates;
import org.geojsf.xml.gml.Point;
import org.geojsf.xml.ogc.DWithin;
import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.ogc.Filter;
import org.geojsf.xml.ogc.PropertyName;
import org.geojsf.xml.wfs.GetFeature;
import org.geojsf.xml.wfs.Query;


public class PointQueryFactory
{	
	private Coordinates coordinates;
	private Distance distance;
	
	public void setDistance(Distance distance) {this.distance = distance;}
	public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}

	public PointQueryFactory()
	{
		
	}
	
	public GetFeature createGetFeature(String typeName, List<String> properties)
	{
		GetFeature gFeature = new GetFeature();
		gFeature.setService("WFS");
		gFeature.setVersion("1.0.0");
		gFeature.setOutputFormat("GML2");
		
		gFeature.setQuery(createQuery(typeName,properties));
		
		return gFeature;
	}
	

    /**
     * @depreciated
     */	
	public static GetFeature cGetFeature(String typeName, List<String> properties, Coordinates coordinates, Distance distance)
	{
		PointQueryFactory pwf = new PointQueryFactory();
		pwf.setCoordinates(coordinates);
		pwf.setDistance(distance);
		return pwf.createGetFeature(typeName, properties);
	}
	
	private Query createQuery(String typeName, List<String> properties)
	{
		Query q = new Query();
		q.setTypeName(typeName);
		
		for(String s : properties)
		{
			PropertyName pn = new PropertyName();
			pn.setValue(s);
			q.getPropertyName().add(pn);
		}
		
		q.setFilter(createFilter());
		
		return q;
	}
	
	private Filter createFilter()
	{
		Filter filter = new Filter();
		filter.setDWithin(createDWithin(distance));
		return filter;
	}
	
	private DWithin createDWithin(Distance distance)
	{
		DWithin dw = new DWithin();
		
		PropertyName pn = new PropertyName();
		pn.setValue("the_geom");
		dw.setPropertyName(pn);
		
		dw.setPoint(createPoint());
		dw.setDistance(distance);
		
		return dw;
	}
	
	private Point createPoint()
	{
		Point point = new Point();
		
		point.setSrsName("http://www.opengis.net/gml/srs/epsg.xml#4326");
		point.setCoordinates(coordinates);
		
		return point;
	}
}
