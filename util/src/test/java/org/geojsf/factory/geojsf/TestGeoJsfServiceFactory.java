package org.geojsf.factory.geojsf;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGeoJsfServiceFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestGeoJsfServiceFactory.class);
	
	private static String[] langKeys = {"de","en"};
	
	private DefaultGeoJsfService service1;
	private DefaultGeoJsfService service2;
	private List<DefaultGeoJsfLayer> layers;
	private DefaultGeoJsfLayer layerA;
	private DefaultGeoJsfLayer layerB;
	
	private GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fService;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		initGenericFactories();
		
		service1 = efService.build("service1", "http://1");
		service2 = efService.build("service2", "http://2");
		layerA = efLayer.build("A", service1, langKeys);
		layerB = efLayer.build("B", service2, langKeys);
		
		layers = new ArrayList<DefaultGeoJsfLayer>();
		layers.add(layerA);
		layers.add(layerB);
		
		fService = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
	}
	
	@Test
	public void size()
	{
		Assert.assertEquals(2, fService.buildFromLayer(layers).size());
		Assert.assertEquals(2, fService.buildFromLayer(layers).size());
	}
}