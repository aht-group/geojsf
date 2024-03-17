package org.geojsf.factory.txt;

import java.time.LocalDate;

import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtEcqlFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtEcqlFactory.class);
	
	
	@Test
	public void testInt()
	{
		LocalDate ld = LocalDate.now();
		logger.info(TxtEcqlFactory.utcDateTime(ld));
	}
	
	public static void main(String[] args)
	{
		GeoJsfBootstrap.init();
		TestTxtEcqlFactory test = new TestTxtEcqlFactory();
		
		test.testInt();
		
	}
}