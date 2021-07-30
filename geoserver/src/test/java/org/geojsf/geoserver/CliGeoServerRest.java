package org.geojsf.geoserver;

import java.io.IOException;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.model.xml.geoserver.Styles;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CliGeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerRest.class);
	
	private GeoServerRest rest;

	public CliGeoServerRest(Configuration config)
	{
		rest = new GeoServerRestWrapper(config);
	}
	
//jeesl.highlight:initDirectly
	public void initDirectly()
	{
		String url = "http://localhost:8080/geoserver";
		String user = "user";
		String password = "password";
		GeoServerRest rest = new GeoServerRestWrapper(url,user,password);
	}
//jeesl.highlight:initDirectly
	
//jeesl.highlight:initWithCommons
	public void initWithCommons(Configuration config)
	{
		GeoServerRest rest = new GeoServerRestWrapper(config);
	}
//jeesl.highlight:initWithCommons
	
	public void test() throws IOException
	{
		Styles layers = rest.styles();
//		JaxbUtil.info(layers);
		
		Document sld = rest.style("grass");
		JDomUtil.debug(sld);
	}
		
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		CliGeoServerRest rest = new CliGeoServerRest(config);
		rest.test();
	}
}