package org.geojsf.factory.txt;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
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
	
	private DefaultGeoJsfService service;
	private DefaultGeoJsfLayer layerA;
	private DefaultGeoJsfLayer layerB;
	
	private DefaultGeoJsfService serviceList;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		initGenericFactories();
		
		service = fService.build("code1", "http://1");
		layerA = fLayer.build("A", service, langKeys);
		layerB = fLayer.build("B", service, langKeys);
		

	}
	
	@Test
	public void single()
	{
		serviceList = new DefaultGeoJsfService();
		serviceList.getLayer().add(layerA);
		
		String expected = layerA.getCode();
		String actual = TxtOpenlayersLayerFactory.buildLayerString(serviceList);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void multi()
	{
		serviceList = new DefaultGeoJsfService();
		serviceList.getLayer().add(layerA);
		serviceList.getLayer().add(layerB);
		
		String expected = layerA.getCode()+","+layerB.getCode();
		String actual = TxtOpenlayersLayerFactory.buildLayerString(serviceList);
		Assert.assertEquals(expected, actual);
	}
}