package org.geojsf.factory.txt;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;

import org.geojsf.model.pojo.geojsf.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.DefaultGeoJsfService;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		String actual = TxtOpenlayersLayerFactory.buildLayerString(service);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void multi()
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		service.getLayer().add(layerA);
		service.getLayer().add(layerB);
		
		String expected = layerB.getCode()+","+layerA.getCode();
		String actual = TxtOpenlayersLayerFactory.buildLayerString(service);
		Assert.assertEquals(expected, actual);
	}
}