package org.geojsf.controller.util;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.factory.xml.openlayers.XmlRepositoryFactory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfEjbTest;
import org.geojsf.util.query.OpenLayersQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfMapLayerFactory extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJsfMapLayerFactory.class);
	
	private GeoJsfServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fJsf;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		fJsf = GeoJsfServiceFactory.factory(DefaultGeoJsfService.class);
		view = DummyViewFactory.build();
	}
	
	private DefaultGeoJsfMap view;
	
	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = fJsf.build(view);
		
		Assert.assertEquals(2, actual.size());
		
		XmlRepositoryFactory f = new XmlRepositoryFactory(OpenLayersQuery.get(OpenLayersQuery.Key.repositoryService, null));
		JaxbUtil.error(f.build(actual));
    }
	
	@Test
	public void serviceOrdering()
	{
		List<DefaultGeoJsfService> actual = fJsf.build(view);
		Assert.assertEquals(DummyViewFactory.serviceOsm.getCode(), actual.get(1).getCode());
		Assert.assertEquals(DummyViewFactory.serviceAht.getCode(), actual.get(0).getCode());
	}
	
	@Test
	public void layerOrdering()
	{
		int i=0;
		for(DefaultGeoJsfService service : fJsf.build(view))
		{
			for(DefaultGeoJsfLayer layer : service.getLayer())
			{
				Assert.assertEquals(view.getLayer().get(i).getLayer().getCode(), layer.getCode());
				i++;
			}
		}
	}
}
