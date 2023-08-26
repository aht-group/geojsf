package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class GeoJsfGeoServerTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGeoServerTestBootstrap.class);
	
	public static Configuration init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("config.geojsf-geoserver.test");
			loggerInit.init();
			
		logger.warn("NS Prefix mapper deavtivated");
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
			ConfigLoader.add(ccp.toFile("geoserver"));
		}
		catch (ExlpConfigurationException e) {logger.warn("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		ConfigLoader.add("config.geojsf-geoserver.test/geoserver.xml");
		
		Configuration config = ConfigLoader.init();
		return config;
	}
}