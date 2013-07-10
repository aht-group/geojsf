package org.geojsf.geoserver;

import org.apache.commons.configuration.Configuration;
import org.geojsf.geoserver.manager.GeoServerCapabilities;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.util.GeoServerConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerCapabilities
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerCapabilities.class);
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		GeoServerCapabilities gsc = new GeoServerCapabilities(config.getString(GeoServerConfigKeys.restHost));
		gsc.debug();
	}
}