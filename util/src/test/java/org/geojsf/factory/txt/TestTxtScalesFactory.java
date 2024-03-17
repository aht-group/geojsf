package org.geojsf.factory.txt;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.model.xml.geojsf.Scales;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtScalesFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtScalesFactory.class);
	
	private Scales scales;
	private TxtScalesFactory txtScales;
	
	@Before
	public void init()
	{
		scales = new Scales();
		scales.setUnit("m");
		
		scales.getScale().add(XmlScaleFactory.build(1000));
		scales.getScale().add(XmlScaleFactory.build(100000));
		
		txtScales = new TxtScalesFactory();
	}
	
	@Test
	public void testInt()
	{
		String expected = "1000, 100000";
		String actual = txtScales.build(scales);
		
		logger.debug(JaxbUtil.toString(scales));
		logger.debug("Factory: "+actual);
		
		Assert.assertEquals(expected, actual);
	}
	
	public static void main(String[] args)
	{
		GeoJsfBootstrap.init();
		TestTxtScalesFactory test = new TestTxtScalesFactory();
		test.init();
		test.testInt();
	}
}