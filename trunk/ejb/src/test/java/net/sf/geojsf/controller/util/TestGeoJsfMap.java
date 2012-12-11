package net.sf.geojsf.controller.util;

import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.controller.factory.xml.openlayers.XmlRepositoryFactory;
import net.sf.geojsf.controller.util.query.OpenLayersQuery;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.test.AbstractGeoJsfEjbTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGeoJsfMap extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestGeoJsfMap.class);
	
	private GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> geoJsfMap;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		view = DummyViewFactory.build();
		geoJsfMap = GeoJsfMap.factory(DefaultGeoJsfService.class, view);
	}
	
	private DefaultGeoJsfView view;
	
	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = geoJsfMap.getLayerServices();
		Assert.assertEquals(2, actual.size());
		
		geoJsfMap.debug();
    }
	
	@Test
	public void serviceOrdering()
	{
		List<DefaultGeoJsfService> actual = geoJsfMap.getLayerServices();
		Assert.assertEquals(DummyViewFactory.serviceOsm.getCode(), actual.get(0).getCode());
		Assert.assertEquals(DummyViewFactory.serviceAht.getCode(), actual.get(1).getCode());
	}
	
	@Test
	public void layerOrdering()
	{
		geoJsfMap.debug();
		int i=0;
		for(DefaultGeoJsfService service : geoJsfMap.getLayerServices())
		{
			for(DefaultGeoJsfLayer layer : service.getLayer())
			{
				Assert.assertEquals(view.getLayer().get(i).getLayer().getCode(), layer.getCode());
				i++;
			}
		}
	}
}
