package org.geojsf.client;

import java.util.Iterator;

import net.sf.ahtutils.controller.UtilsJbossFacadeLookup;
import net.sf.exlp.util.config.ConfigKey;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.xml.GeoJsfNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoBootstrap.class);
	public final static String xmlConfig = "config.geojsf-demo.test/geo.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("config.geojsf-demo.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());		
		
		ConfigLoader.add(configFile);
		
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("geojsf","demo").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		
		Configuration config = ConfigLoader.init();		

		logger.debug("Config and Logger initialized");
		checkConfig(config);
		return config;
	}
	
	public static UtilsJbossFacadeLookup buildFacadeLookup(Configuration config)
	{
		return new UtilsJbossFacadeLookup("","geojsf",
				config.getString(ConfigKey.netEjbHost),
				config.getString(ConfigKey.netEjbUser),
				config.getString(ConfigKey.netEjbPwd));
	}
	
	private static void checkConfig(Configuration config)
	{
		if(!config.getString(ConfigKey.netEjbHost).equals("localhost"))
		{
			logger.warn("**** Using non-localhost connection to: "+config.getString(ConfigKey.netEjbHost));
			try {Thread.sleep(5*1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}