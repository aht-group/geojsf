package org.geojsf.controller.util;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.xml.openlayers.XmlRepositoryFactory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfEjbTest;
import org.geojsf.test.GeoJsfEjbTestBootstrap;
import org.geojsf.util.GeojsfDatastructureDebugger;
import org.geojsf.util.query.GeoJsfQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDummyViewFactory extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDummyViewFactory.class);
	
	private GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fJsf;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		fJsf = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
		map = DummyViewFactory.build();
	}
	
	private DefaultGeoJsfMap map;
	
	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = fJsf.build(map);
		
		Assert.assertEquals(2, actual.size());
		
		XmlRepositoryFactory f = new XmlRepositoryFactory(GeoJsfQuery.get(GeoJsfQuery.Key.repositoryService, null));
		JaxbUtil.error(f.build(actual));
    }
	
	@Test
	public void serviceOrdering()
	{
		List<DefaultGeoJsfService> actual = fJsf.build(map);
		Assert.assertEquals(DummyViewFactory.serviceOsm.getCode(), actual.get(1).getCode());
		Assert.assertEquals(DummyViewFactory.serviceAht.getCode(), actual.get(0).getCode());
	}
	
	@Test
	public void layerOrdering()
	{
		int i=0;
		for(DefaultGeoJsfService service : fJsf.build(map))
		{
			for(DefaultGeoJsfLayer layer : service.getLayer())
			{
				Assert.assertEquals(map.getViews().get(i).getLayer().getCode(), layer.getCode());
				i++;
			}
		}
	}
	
	public static void main(String[] args)
	{
		GeoJsfEjbTestBootstrap.init();
		
		DefaultGeoJsfMap map = DummyViewFactory.build();
		
		logger.info("***********************");
		logger.info("This is the MAP");
		GeojsfDatastructureDebugger.debug(map);
		
		GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fService;
		fService = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
		
		logger.info("***********************");
		logger.info("This is the generated SERVICE list");
		List<DefaultGeoJsfService> list = fService.build(map);
		for(DefaultGeoJsfService service : list)
		{
			GeojsfDatastructureDebugger.debug(service);
		}
		
	}
}