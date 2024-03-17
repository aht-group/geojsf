package org.geojsf.geoserver;

import org.apache.commons.configuration.Configuration;
import org.geojsf.geoserver.manager.GeoServerCapabilities;
import org.geojsf.test.GeoJsfBootstrap;
import org.geojsf.util.GeoServerConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerCapabilities
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerCapabilities.class);
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfBootstrap.init();
			
		GeoServerCapabilities gsc = new GeoServerCapabilities(config.getString(GeoServerConfigKeys.restUrl));
		gsc.debug();
	}
}