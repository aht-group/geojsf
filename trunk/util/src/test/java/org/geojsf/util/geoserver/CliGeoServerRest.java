package org.geojsf.util.geoserver;

import java.io.IOException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfUtilsTestBootstrap;

import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.geoserver.GeoServerRestWrapper;
import org.geojsf.xml.geoserver.Styles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerRest.class);
	
	private GeoServerRest rest;

	public CliGeoServerRest()
	{
		rest = new GeoServerRestWrapper("http://","", "");
		
	}
	
	public void test() throws IOException
	{
		Styles layers = rest.styles();
		JaxbUtil.info(layers);
	}
		
	public static void main (String[] args) throws Exception
	{
		GeoJsfUtilsTestBootstrap.init();
			
		CliGeoServerRest rest = new CliGeoServerRest();
		rest.test();
	}
}