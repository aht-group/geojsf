package net.sf.geojsf.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfUtilsTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfUtilsTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("config.geojsf-util.test");
			loggerInit.init();
	}
}