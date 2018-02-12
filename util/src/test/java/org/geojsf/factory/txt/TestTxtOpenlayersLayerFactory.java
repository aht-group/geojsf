package org.geojsf.factory.txt;

import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;

public class TestTxtOpenlayersLayerFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtOpenlayersLayerFactory.class);
	
	private static String[] langKeys = {"de","en"};
	
	private DefaultGeoJsfCategory cat1;
	private DefaultGeoJsfService service;
	private DefaultGeoJsfLayer layerA;
	private DefaultGeoJsfLayer layerB;
	
	@Before
	public void init() throws UtilsConstraintViolationException
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