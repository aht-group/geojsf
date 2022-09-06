package org.geojsf.controller.util;

import java.util.List;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.xml.openlayers.XmlRepositoryFactory;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfScale;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.io.graphic.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.io.graphic.DefaultGeoJsfGraphicComponent;
import org.geojsf.model.pojo.io.graphic.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.io.graphic.DefaultGeoJsfGraphicType;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfEjbTest;
import org.geojsf.test.GeoJsfEjbTestBootstrap;
import org.geojsf.util.query.GeoJsfQuery;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDummyViewFactory extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDummyViewFactory.class);
	
	private GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule> fJsf;
	private DummyViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicComponent,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule> dvf;
	
	
	@Before
	public void init() throws JeeslConstraintViolationException
	{
		fJsf = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
//		dvf = DummyViewFactory.factory(GeoFactoryProvider.
//				DefaultGeoJsfLang.class,DefaultGeoJsfDescription.class,DefaultGeoJsfCategory.class,DefaultGeoJsfService.class,DefaultGeoJsfLayer.class,DefaultGeoJsfMap.class,DefaultGeoJsfView.class);
		map = dvf.getMap();
	}
	
	private DefaultGeoJsfMap map;
	public DefaultGeoJsfMap getMap() {return map;}

	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = fJsf.build(map);
		
		Assert.assertEquals(2, actual.size());
		
		XmlRepositoryFactory f = new XmlRepositoryFactory(GeoJsfQuery.get(GeoJsfQuery.Key.repositoryService, null));
		logger.error("NYI, uncommented");
//		JaxbUtil.error(f.build(actual));
    }
	
	@Test
	public void serviceOrdering()
	{
		List<DefaultGeoJsfService> actual = fJsf.build(map);
		Assert.assertEquals(dvf.getServiceOsm().getCode(), actual.get(1).getCode());
		Assert.assertEquals(dvf.getServiceAht().getCode(), actual.get(0).getCode());
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
	
	public static void main(String[] args) throws JeeslConstraintViolationException
	{
		GeoJsfEjbTestBootstrap.init();
		
		TestDummyViewFactory tdv = new TestDummyViewFactory();
		tdv.init();
		DefaultGeoJsfMap map = tdv.getMap();
		
		logger.info("***********************");
		logger.info("This is the MAP");
		logger.warn("NYI, deactivated");
//		GeojsfDatastructureDebugger.debug(map);
		
		GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule> fService;
		fService = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
		
		logger.info("***********************");
		logger.info("This is the generated SERVICE list");
		List<DefaultGeoJsfService> list = fService.build(map);
		for(DefaultGeoJsfService service : list)
		{
			logger.error("NYI, uncommented");
//			GeojsfDatastructureDebugger.debug(service);
		}
		
	}
}
