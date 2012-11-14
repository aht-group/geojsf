package net.sf.geojsf.util.factory.jsf;

import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.test.AbstractGeoJsfUtilTest;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;
import net.sf.geojsf.util.factory.xml.openlayers.XmlRepositoryFactory;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfLayerFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJsfLayerFactory.class);
	
	private JsfLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fJsf;
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fView;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		fJsf = JsfLayerFactory.factory();
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLayer.class);
		fView = EjbGeoViewFactory.factory(DefaultGeoJsfView.class);
		
		DefaultGeoJsfService s1 = fService.create("code1", "url1");
		DefaultGeoJsfService s2 = fService.create("code2", "url2");
		DefaultGeoJsfService s3 = fService.create("code3", "url3");
		
		DefaultGeoJsfLayer l1 = fLayer.create("l1", s1);
		DefaultGeoJsfLayer l2 = fLayer.create("l2", s1);
		DefaultGeoJsfLayer l3 = fLayer.create("l3", s1);
		DefaultGeoJsfLayer l4 = fLayer.create("l4", s2);
		DefaultGeoJsfLayer l5 = fLayer.create("l5", s2);
		DefaultGeoJsfLayer l6 = fLayer.create("l6", s3);
		
		view = fView.create("view");
		view.getLayer().add(l1);
		view.getLayer().add(l2);
		view.getLayer().add(l3);
		view.getLayer().add(l4);
		view.getLayer().add(l5);
		view.getLayer().add(l6);
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
