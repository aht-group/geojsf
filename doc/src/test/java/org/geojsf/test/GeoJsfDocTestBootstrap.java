package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;

public class GeoJsfDocTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDocTestBootstrap.class);
		
	public final static String xmlConfig = "config.geojsf-doc.test/geojsf.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.path("config.geojsf-doc.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
			ConfigLoader.addFile(ccp.toFile("doc"));
		}
		catch (ExlpConfigurationException e) {logger.info("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}
		ConfigLoader.addString(configFile);
		
		Configuration config = ConfigLoader.init();
		
//		config.setProperty(UtilsDocumentation.keyTranslationFile, "/doc/src/main/resources/dss.doc/translations.xml");
//		config.setProperty(UtilsDocumentation.keyBaseDocDir, "../doc/src/main/latex/common");
//		config.setProperty(GeoJsfDocumentation.keyBaseDocDir, config.getProperty(UtilsDocumentation.keyBaseDocDir));
		
		logger.debug("Config and Logger initialized");
		
		return config;
	}
	
}