package org.geojsf.geoserver;

import java.io.IOException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.xml.geoserver.Styles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerRest.class);
	
	private GeoServerRest rest;

	public CliGeoServerRest(Configuration config)
	{
		rest = new GeoServerRestWrapper(config);
		
	}
	
	public void test() throws IOException
	{
		Styles layers = rest.styles();
		JaxbUtil.info(layers);
	}
		
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		CliGeoServerRest rest = new CliGeoServerRest(config);
		rest.test();
	}
}