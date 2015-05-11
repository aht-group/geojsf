package org.geojsf.util.wms;

import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.factory.xml.geojsf.XmlViewPortFactory;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWmsTimeDownloader extends AbstractGeoJsfUtilTest
{
	private final static Logger logger = LoggerFactory.getLogger(GeoJsfUtilsTestBootstrap.class);
	
	private WmsTileDownloader wmsDownloader;
	private ViewPort viewPort;
	
	@Before
	public void init()
	{
		wmsDownloader = new WmsTileDownloader(null);

		viewPort = XmlViewPortFactory.build(10,20);
		viewPort.setScale(XmlScaleFactory.build(50000));
	}
	
	@Test
	public void calculateResolution()
	{
		int scale = 50000;
		int dotsPerInch = 72;
		double expected = 0.00015873908440210453;
		double actual = wmsDownloader.calculateResolution(scale, dotsPerInch);
		Assert.assertEquals(expected, actual,0.00001);
	}
	
	@Test
	public void calculateBoundingBox()
	{
		wmsDownloader.calculateBoundingBox(viewPort, 600, 400);
		logger.info("left: "+viewPort.getLeft());
	}
	
	
}
