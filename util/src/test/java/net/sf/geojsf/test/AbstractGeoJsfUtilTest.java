package net.sf.geojsf.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.xml.GeoJsfNsPrefixMapper;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfUtilTest.class);
	
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
}