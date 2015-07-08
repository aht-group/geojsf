package org.geojsf.factory.geojsf;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;

import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldStyle;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
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
	
	private DefaultGeoJsfCategory cat1;
	private DefaultGeoJsfService service1;
	private DefaultGeoJsfService service2;
	private List<DefaultGeoJsfLayer> layers;
	private DefaultGeoJsfLayer layerA;
	private DefaultGeoJsfLayer layerB;
	
	private GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfSldType,DefaultGeoJsfSldStyle,DefaultGeoJsfSldTemplate> fService;
	
	@Before
	public void init() throws UtilsConstraintViolationException
	{
		initGenericFactories();
		
		cat1 = efCategory.build("cat");
		service1 = efService.build("service1", "http://1");
		service2 = efService.build("service2", "http://2");
		layerA = efLayer.build("A", service1, cat1, langKeys);
		layerB = efLayer.build("B", service2, cat1, langKeys);
		
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