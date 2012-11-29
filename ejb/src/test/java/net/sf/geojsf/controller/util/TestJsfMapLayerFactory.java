package net.sf.geojsf.controller.util;

import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.test.AbstractGeoJsfEjbTest;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewLayerFactory;
import net.sf.geojsf.util.factory.xml.openlayers.XmlRepositoryFactory;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfMapLayerFactory extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJsfMapLayerFactory.class);
	
	private GeoJsfMapLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fJsf;
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fView;
	private EjbGeoViewLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fViewLayer;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		fJsf = GeoJsfMapLayerFactory.factory(DefaultGeoJsfService.class);
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLayer.class);
		fView = EjbGeoViewFactory.factory(DefaultGeoJsfView.class);
		fViewLayer = EjbGeoViewLayerFactory.factory(DefaultGeoJsfViewLayer.class);
		
		DefaultGeoJsfService s1 = fService.create("code1", "url1");
		DefaultGeoJsfService s2 = fService.create("code2", "url2");
		DefaultGeoJsfService s3 = fService.create("code3", "url3");
		DefaultGeoJsfService s4 = fService.create("code4", "url3");
		
		DefaultGeoJsfLayer l1 = fLayer.create("l1", s1);
		DefaultGeoJsfLayer l2 = fLayer.create("l2", s1);
		DefaultGeoJsfLayer l3 = fLayer.create("l3", s1);
		DefaultGeoJsfLayer l4 = fLayer.create("l4", s2);
		DefaultGeoJsfLayer l5 = fLayer.create("l5", s2);
		DefaultGeoJsfLayer l6 = fLayer.create("l6", s3);
		DefaultGeoJsfLayer l7 = fLayer.create("l7", s4);
		
		view = fView.create("view");
		view.getLayer().add(fViewLayer.create(view, l1, 1, true));
		view.getLayer().add(fViewLayer.create(view, l2, 2, true));
		view.getLayer().add(fViewLayer.create(view, l3, 3, true));
		view.getLayer().add(fViewLayer.create(view, l4, 4, true));
		view.getLayer().add(fViewLayer.create(view, l5, 5, true));
		view.getLayer().add(fViewLayer.create(view, l6, 6, true));
		view.getLayer().add(fViewLayer.create(view, l7, 7, true));
	}
	
	private DefaultGeoJsfView view;
	
	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = fJsf.build(view);
		
		Assert.assertEquals(3, actual.size());
		
		XmlRepositoryFactory f = new XmlRepositoryFactory();
		JaxbUtil.error(f.build(actual));
    }
}
