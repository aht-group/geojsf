package org.geojsf.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.jeesl.controller.handler.system.property.ConfigBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfBootstrap.class);
		
	
	private static org.exlp.interfaces.system.property.Configuration config;
	
	public static org.exlp.interfaces.system.property.Configuration wrap()
	{
		LoggerBootstrap.instance().path("geojsf/system/io/log").init();
		
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("geojsf").jaxb(JaxbUtil.instance());
		
		ConfigBootstrap cb = ConfigBootstrap.instance().add(ccp.toPath("client")).add("config.geojsf-geoserver.test/geoserver.xml");
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
		
		 config = cb.wrap();

		
//		Configuration config = ConfigLoader.init();
		return config;
	}
}