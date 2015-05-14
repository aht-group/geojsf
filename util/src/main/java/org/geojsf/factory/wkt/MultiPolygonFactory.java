package org.geojsf.factory.wkt;

import org.geojsf.model.xml.geojsf.Wkt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class MultiPolygonFactory
{
	final static Logger logger = LoggerFactory.getLogger(MultiPolygonFactory.class);
	
	private GeometryFactory gf;
	private WKTReader wktReader;
	
	public MultiPolygonFactory(GeometryFactory gf)
	{
		this.gf=gf;
		wktReader = new WKTReader();
	}
	
	public MultiPolygonFactory()
	{
		PrecisionModel pm = new PrecisionModel(PrecisionModel.FLOATING);
		gf = new GeometryFactory(pm,4326);
		wktReader = new WKTReader();
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
    
    public MultiPolygon build(Wkt wkt) throws ParseException
    {
		return build(wkt.getValue());
    }
    
    public MultiPolygon build(String wkt) throws ParseException
    {
		Geometry geometry = wktReader.read(wkt);
		geometry.setSRID(4326);
		return (MultiPolygon)geometry;
    }
    
}