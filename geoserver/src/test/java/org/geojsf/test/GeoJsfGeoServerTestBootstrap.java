package org.geojsf.test;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.geoserver.GeoJsfGeoServerPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfGeoServerTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGeoServerTestBootstrap.class);
	
	public static Configuration init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("config.geojsf-geoserver.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new GeoJsfGeoServerPrefixMapper());
		
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("geojsf","geoserver").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.warn("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		ConfigLoader.add("config.geojsf-geoserver.test/geoserver.xml");
		
		Configuration config = ConfigLoader.init();
		return config;
	}
}