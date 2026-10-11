package org.geojsf.factory.wkt;

import org.geojsf.factory.xml.geojsf.XmlCoordinateFactory;
import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Point;

public class TestWktPointFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWktPointFactory.class);
	
	private PointFactory pf;
	
	@BeforeEach
	public void init()
	{
		pf = new PointFactory();
	}
	
	@Test
	public void value()
	{
		double lat=20;
		double lon=10;
		Point point = pf.build(lat,lon);
		Coordinate cDirect = XmlCoordinateFactory.build(point);
		
		Assertions.assertEquals(lat,cDirect.getLat(),0.5);
	}
}