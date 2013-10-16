package org.geojsf.factory.wkt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

public class MultiPolygonFactory
{
	final static Logger logger = LoggerFactory.getLogger(MultiPolygonFactory.class);
	
	private GeometryFactory gf;
	
	public MultiPolygonFactory(GeometryFactory gf)
	{
		this.gf=gf;
	}
	
    public MultiPolygon build(Geometry geometry)
    {
    	MultiPolygon mp;
    	if(geometry instanceof Polygon)
		{
			Polygon[] aP= new Polygon[1];
			aP[0] = (Polygon)geometry;
			mp = gf.createMultiPolygon(aP);
		}
		else
		{
			mp = (MultiPolygon)geometry;
		}
    	return mp;
    } 
    
}