package org.geojsf.test;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfUtilsTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfUtilsTestBootstrap.class);
	
	public static Configuration init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config.geojsf-util.test");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
			ConfigLoader.add(ccp.toFile("util"));
		}
		catch (ExlpConfigurationException e) {e.printStackTrace();}
		ConfigLoader.add("config.geojsf-util.test/geojsf.xml");
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");
		return config;
	}
}