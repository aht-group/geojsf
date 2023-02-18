package org.geojsf.factory.txt;

import org.geojsf.model.pojo.geojsf.core.DfGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.core.DfGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtOpenlayersLayerFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtOpenlayersLayerFactory.class);
	
	private static String[] langKeys = {"de","en"};
	
	private DfGeoJsfCategory cat1;
	private DefaultGeoJsfService service;
	private DfGeoJsfLayer layerA;
	private DfGeoJsfLayer layerB;
	
	@Before
	public void init() throws JeeslConstraintViolationException
	{
		initGenericFactories();
		
		cat1 = efCategory.build("cat");
		service = efService.build("code1", "http://1");
		layerA = efLayer.build("A", service, cat1, langKeys);
		layerB = efLayer.build("B", service, cat1, langKeys);
	}
	
	@Test
	public void single()
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		service.getLayer().add(layerA);
		
		String expected = layerA.getCode();
		logger.error("NYI, uncommented");
		String actual = null;//TxtOpenlayersLayerFactory.buildLayerString(service);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void multi()
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		service.getLayer().add(layerA);
		service.getLayer().add(layerB);
		
		String expected = layerB.getCode()+","+layerA.getCode();
		logger.error("NYI, uncommented");
		String actual = null;//TxtOpenlayersLayerFactory.buildLayerString(service);
		Assert.assertEquals(expected, actual);
	}
}