package org.geojsf.test;

import org.geojsf.factory.GeoDefaultFactoryProvider;
import org.geojsf.factory.ejb.core.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.core.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfUtilTest.class);
	
	protected EjbGeoCategoryFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory> efCategory;
	protected EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService> efService;
	protected EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer> efLayer;
	protected EjbGeoMapFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfMap> efMap;
	protected EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> efView;
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.geojsf-util.test");
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
		efCategory = GeoDefaultFactoryProvider.geoCore().ejbCategory();
		efService = GeoDefaultFactoryProvider.geoCore().ejbService();
		efLayer = GeoDefaultFactoryProvider.geoCore().ejbLayer();
		efMap = GeoDefaultFactoryProvider.geoCore().ejbMap();
		efView = GeoDefaultFactoryProvider.geoCore().ejbView();
	}
}