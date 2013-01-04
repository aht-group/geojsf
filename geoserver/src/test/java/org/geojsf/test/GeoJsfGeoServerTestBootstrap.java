package org.geojsf.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfGeoServerTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGeoServerTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("config.geojsf-geoserver.test");
			loggerInit.init();
	}
}