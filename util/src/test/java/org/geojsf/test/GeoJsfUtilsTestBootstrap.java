package org.geojsf.test;

import net.sf.exlp.exception.ExlpConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfUtilsTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfUtilsTestBootstrap.class);
	
	public static Configuration init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.path("config.geojsf-util.test");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
			ConfigLoader.addFile(ccp.toFile("util"));
		}
		catch (ExlpConfigurationException e) {e.printStackTrace();}
		ConfigLoader.addString("config.geojsf-util.test/geojsf.xml");
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");
		return config;
	}
}