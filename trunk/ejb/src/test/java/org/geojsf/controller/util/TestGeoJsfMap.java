package org.geojsf.controller.util;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfEjbTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGeoJsfMap extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestGeoJsfMap.class);
	
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fLayer;	
	
	private GeoJsfMapHelper<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView>
			gsmView,gjmLayer;
	
	@Before
	public void init() throws UtilsIntegrityException
	{	
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfLayer.class);
		
		view = DummyViewFactory.build();
		gsmView = GeoJsfMapHelper.factory(DefaultGeoJsfService.class, view);
			
		service = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");
		layer = fLayer.build("basic", service,DefaultGeoJsfLang.defaultLangs);layer.setId(1);
		gjmLayer = GeoJsfMapHelper.build(DefaultGeoJsfService.class, DefaultGeoJsfMap.class, DefaultGeoJsfView.class, layer);
	}
	
	private DefaultGeoJsfMap view;
	public static DefaultGeoJsfService service;
	private DefaultGeoJsfLayer layer;
	
	@Test
	public void viewBuild()
    {	
		List<DefaultGeoJsfService> actual = gsmView.getLayerServices();
		Assert.assertEquals(2, actual.size());
		
		gsmView.debug();
    }
	
	@Test
	public void viewServiceOrdering()
	{
		List<DefaultGeoJsfService> actual = gsmView.getLayerServices();
		Assert.assertEquals(DummyViewFactory.serviceAht.getCode(), actual.get(0).getCode());
		Assert.assertEquals(DummyViewFactory.serviceOsm.getCode(), actual.get(1).getCode());
	}
	
	@Test @Ignore
	public void viewLayerOrdering()
	{
		gsmView.debug();
		
		for(DefaultGeoJsfService service : gsmView.getLayerServices())
		{
			int i=service.getLayer().size()-1;
			for(DefaultGeoJsfLayer layer : service.getLayer())
			{
				Assert.assertEquals(view.getViews().get(i).getLayer().getCode(), layer.getCode());
				i--;
			}
		}
	}
	
	@Test
	public void layer()
	{
		gjmLayer.debug();
	}
}