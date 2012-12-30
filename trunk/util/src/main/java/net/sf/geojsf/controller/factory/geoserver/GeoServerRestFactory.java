package net.sf.geojsf.controller.factory.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import java.io.Serializable;
import java.net.MalformedURLException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.GeoServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestFactory.class);
	public static final long serialVersionUID=1;
	
	public static GeoServerRESTReader reader(Configuration config) throws MalformedURLException
	{
		return new GeoServerRESTReader(config.getString(GeoServerConfig.restHost),
										config.getString(GeoServerConfig.restUser),
										config.getString(GeoServerConfig.restPassword));
	}
	
	public static GeoServerRESTPublisher publisher(Configuration config)
	{
		return new GeoServerRESTPublisher(config.getString(GeoServerConfig.restHost),
											config.getString(GeoServerConfig.restUser),
											config.getString(GeoServerConfig.restPassword));
	}
}