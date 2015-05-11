package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.model.xml.geojsf.Wkt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTWriter;

public class XmlWktFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlWktFactory.class);
	
	public static final long serialVersionUID=1;
	

	public static Wkt build(Geometry geometry)
	{
		WKTWriter w = new WKTWriter();
		Wkt wkt = new Wkt();
		
		wkt.setType(geometry.getClass().getSimpleName());
		wkt.setValue(w.write(geometry));

		return wkt;
	}
	
	public static Wkt build(String type)
	{
		Wkt wkt = new Wkt();	
		wkt.setValue(type);
		return wkt;
	}
}