package org.geojsf.test;

import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfXmlTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfXmlTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.path("config.geojsf-entities.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
}