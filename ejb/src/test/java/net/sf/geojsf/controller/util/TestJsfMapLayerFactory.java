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
import net.sf.geojsf.util.factory.xml.openlayers.XmlRepositoryFactory;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfMapLayerFactory extends AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJsfMapLayerFactory.class);
	
	private GeoJsfMapLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fJsf;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		fJsf = GeoJsfMapLayerFactory.factory(DefaultGeoJsfService.class);
		view = DummyViewFactory.build();
	}
	
	private DefaultGeoJsfView view;
	
	@Test
	public void test()
    {	
		List<DefaultGeoJsfService> actual = fJsf.build(view);
		
		
		Assert.assertEquals(2, actual.size());
		
		XmlRepositoryFactory f = new XmlRepositoryFactory();
		JaxbUtil.error(f.build(actual));
    }
}
