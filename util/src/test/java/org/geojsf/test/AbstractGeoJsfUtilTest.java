package org.geojsf.test;

import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfUtilTest.class);
	
//	protected EjbGeoCategoryFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfCategory> efCategory;
//	protected EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService> efService;
//	protected EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfCategory,DefaultGeoJsfService,DfGeoJsfLayer> efLayer;
//	protected EjbGeoMapFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfMap> efMap;
//	protected EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> efView;
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.path("config.geojsf-util.test");
			loggerInit.init();
		}
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
	
	protected void initGenericFactories()
	{
//		efCategory = GeoDefaultFactoryProvider.geoCore().ejbCategory();
//		efService = GeoDefaultFactoryProvider.geoCore().ejbService();
//		efLayer = GeoDefaultFactoryProvider.geoCore().ejbLayer();
//		efMap = GeoDefaultFactoryProvider.geoCore().ejbMap();
//		efView = GeoDefaultFactoryProvider.geoCore().ejbView();
	}
}