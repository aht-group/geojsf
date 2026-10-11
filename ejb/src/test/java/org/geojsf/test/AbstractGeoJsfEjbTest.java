package org.geojsf.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfEjbTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfEjbTest.class);
	
	@BeforeAll
    public static void initLogger()
	{
		if(!LoggerBootstrap.isLog4jInited())
		{
			LoggerBootstrap.instance("cli.xml.log4j2.xml").path("geojsf/system/io/log").init();
		}
    }
	
	@BeforeAll
	public static void initPrefixMapper()
	{
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
}