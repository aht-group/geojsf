package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.xml.geoserver.Styles;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerStyleManager
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerStyleManager.class);
	
	private GeoServerStyleManager styleManager;

	public CliGeoServerStyleManager(Configuration config)
	{
		GeoServerRest rest = new GeoServerRestWrapper(config);
		styleManager = new GeoServerStyleManager(rest);
	}
	
	public void test() throws IOException
	{
		File dir = new File("target");
//		styleManager.exportStyle("grass", new File("target"));
		styleManager.exportStyles(dir);
	}
		
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		CliGeoServerStyleManager rest = new CliGeoServerStyleManager(config);
		rest.test();
	}
}