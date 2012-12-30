package org.geojsf.util;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.controller.factory.geoserver.GeoServerDataStoreFactory;
import net.sf.geojsf.controller.factory.geoserver.GeoServerRestFactory;
import net.sf.geojsf.controller.factory.geoserver.GeoServerWorkspaceFactory;
import net.sf.geojsf.controller.factory.xml.geoserver.XmlDataStoreFactory;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.GeoServerConfig;
import org.geojsf.xml.geoserver.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerConfiguration
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerConfiguration.class);
	
	private Configuration config;
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	public GeoServerConfiguration(Configuration config) throws MalformedURLException
	{
		this.config=config;
		reader = GeoServerRestFactory.reader(config);
		publisher = GeoServerRestFactory.publisher(config);	
	}
	
	public void createWorkspace()
	{
		String workspace = config.getString(GeoServerConfig.workspace);
		GeoServerWorkspaceFactory f = new GeoServerWorkspaceFactory(reader,publisher);
		boolean wsAvailable = f.createWorkspace(workspace);
		logger.info("Workspace "+workspace+" available: "+wsAvailable);
	}
	
	public void createDataStore()
	{
		DataStore ds = XmlDataStoreFactory.build(config);
		JaxbUtil.info(ds);
		
		GeoServerDataStoreFactory f = new GeoServerDataStoreFactory(reader,publisher);
		f.createDataStore(config.getString(GeoServerConfig.workspace),ds);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerConfiguration geoserver = new GeoServerConfiguration(config);
		geoserver.createWorkspace();
		geoserver.createDataStore();
	}
}