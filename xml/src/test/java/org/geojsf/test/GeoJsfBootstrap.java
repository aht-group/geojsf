package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfBootstrap.class);
		
	private enum IoSsiSystem {geojsf}
	
	private static Configuration config;
	
	public static org.exlp.interfaces.system.property.Configuration wrap() {return ConfigLoader.wrap(init());}
	public static Configuration init()
	{
		LoggerBootstrap.instance().path("geojsf/system/io/log").init();
//		LoggerBootstrap.instance("cli.xml.log4j2.xml").path("geojsf/system/io/log").init();
		
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
//		try
//		{
//			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
//			ConfigLoader.addFile(ccp.toFile("geoserver"));
//		}
//		catch (ExlpConfigurationException e) {logger.warn("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
//		ConfigLoader.addString("config.geojsf-geoserver.test/geoserver.xml");
		
		
		try
		{
//			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(IoSsiSystem.geojsf).jaxb(JaxbUtil.instance());
			ConfigLoader configBootstrap = ConfigLoader.instance();
//			configBootstrap.add(ccp.toPath("client"));
//			configBootstrap.add(configFile);
			config = configBootstrap.combine();
		}
		catch (ConfigurationException e) {e.printStackTrace();}
		
//		Configuration config = ConfigLoader.init();
		return config;
	}
}