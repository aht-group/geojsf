package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;

public class GeoJsfBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfBootstrap.class);
		
	public static Configuration init()
	{
		LoggerBootstrap.instance("cli.xml.log4j2.xml").path("geojsf/system/io/log").init();
		
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
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