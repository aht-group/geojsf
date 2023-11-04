package org.geojsf.factory.wkt;

import org.exlp.util.io.JsonUtil;
import org.geojsf.model.xml.geojsf.Wkt;
import org.geojson.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.geojson.GeoJsonReader;

public class MultiPolygonFactory
{
	final static Logger logger = LoggerFactory.getLogger(MultiPolygonFactory.class);
	
	private GeometryFactory gf;
	private WKTReader wktReader;
	private final GeoJsonReader geojsfReader;
	
	public static MultiPolygonFactory instance() {return new MultiPolygonFactory();}
	
	private MultiPolygonFactory()
	{
		PrecisionModel pm = new PrecisionModel(PrecisionModel.FLOATING);
		gf = new GeometryFactory(pm,4326);
		wktReader = new WKTReader();
		geojsfReader = new GeoJsonReader();
	}
	public MultiPolygonFactory(GeometryFactory gf)
	{
		this.gf=gf;
		wktReader = new WKTReader();
		geojsfReader = new GeoJsonReader();
	}
	
    public MultiPolygon toMultiPolygon(Geometry geometry)
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
    
    public  MultiPolygon build(Feature json) throws JsonProcessingException, ParseException
    {
    	Geometry g  = geojsfReader.read(JsonUtil.toString(json.getGeometry()));
		return this.toMultiPolygon(g);
    }
}