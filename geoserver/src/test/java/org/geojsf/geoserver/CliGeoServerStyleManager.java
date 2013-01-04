package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.xml.geoserver.Styles;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CliGeoServerStyleManager
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerStyleManager.class);
	
	private GeoServerStyleManager styleManager;
	
	public CliGeoServerStyleManager(Configuration config, GeoServerRest rest)
	{
		styleManager = init(rest,new File("target"));
	}

//ahtutils.highlight:init
	public GeoServerStyleManager init(GeoServerRest rest, File styleDir)
	{
		GeoServerStyleManager styleManager = new GeoServerStyleManager(rest,styleDir);
		return styleManager;
	}
//ahtutils.highlight:init
	
//ahtutils.highlight:basic
	public void basic(GeoServerRest rest) throws IOException
	{
		Styles styles = rest.styles();
		Styles stylesInWorkspace = rest.styles("ws");
		Document sld = rest.style("grass");
		Document sldInWorkspace = rest.style("ws","grass");
	}
//ahtutils.highlight:basic
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		CliGeoServerStyleManager test = new CliGeoServerStyleManager(config,rest);
		test.basic(rest);
	}
}