package net.sf.geojsf.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.xml.GeoJsfNsPrefixMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfXmlTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfXmlTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
}