package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;

public class GeoJsfGeoServerTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGeoServerTestBootstrap.class);
	
	public static Configuration init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.path("config.geojsf-geoserver.test");
			loggerInit.init();
			
		logger.warn("NS Prefix mapper deavtivated");
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
			ConfigLoader.addFile(ccp.toFile("geoserver"));
		}
		catch (ExlpConfigurationException e) {logger.warn("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		ConfigLoader.addString("config.geojsf-geoserver.test/geoserver.xml");
		
		Configuration config = ConfigLoader.init();
		return config;
	}
}