package org.geojsf.test;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

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
		loggerInit.addAltPath("config.geojsf-doc.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("geojsf","doc").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.info("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}
		ConfigLoader.add(configFile);
		
		Configuration config = ConfigLoader.init();
		
//		config.setProperty(UtilsDocumentation.keyTranslationFile, "/doc/src/main/resources/dss.doc/translations.xml");
//		config.setProperty(UtilsDocumentation.keyBaseDocDir, "../doc/src/main/latex/common");
//		config.setProperty(GeoJsfDocumentation.keyBaseDocDir, config.getProperty(UtilsDocumentation.keyBaseDocDir));
		
		logger.debug("Config and Logger initialized");
		
		return config;
	}
	
}