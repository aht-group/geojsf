package org.geojsf.factory.txt;

import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtOpenlayersLayerFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtOpenlayersLayerFactory.class);
	
	@Before
	public void init()
	{
		DefaultGeoJsfLayer layer = new DefaultGeoJsfLayer();
	}
	
	@Test
	public void single()
	{
		
	}
}