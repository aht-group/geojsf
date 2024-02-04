package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.util.GeoServerConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CliGeoServerDataStoreManager
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerDataStoreManager.class);
	
	private Configuration config;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerRest rest;
	
	public CliGeoServerDataStoreManager(Configuration config, GeoServerRest rest)
	{
		this.config=config;
		this.rest=rest;
		dataStoreManager = init();
	}

	public GeoServerDataStoreManager init()
	{
//jeesl.highlight:init
		GeoServerDataStoreManager dataStoreManager = new GeoServerDataStoreManager(rest);
//jeesl.highlight:init
		return dataStoreManager;
	}
	
	public void basic() throws IOException
	{
		Workspace ws = XmlWorkspaceFactory.build("lis");
		DataStores dataStores;
		boolean available;
//jeesl.highlight:basic		
		dataStores = dataStoreManager.getDataStores(ws);
//jeesl.highlight:basic
		JaxbUtil.info(dataStores);
	}

	public void read() throws IOException
	{
		Workspace ws = XmlWorkspaceFactory.build("lis");
		DataStore ds = XmlDataStoreFactory.build("lcbc-public");
//jeesl.highlight:read
		boolean available = dataStoreManager.isAvailable(ws, ds);
		DataStores dataStores = dataStoreManager.getDataStores(ws);
		
//jeesl.highlight:read
		logger.info("DS available: "+available);
		JaxbUtil.info(dataStores);
	}
	
	public void documentation() throws IOException
	{
		DataStores dataStores = rest.getDataStores(config.getString(GeoServerConfigKeys.workspace));
		for(DataStore ds : dataStores.getDataStore())
		{
			ds.setName(ds.getName().replace(config.getString(GeoServerConfigKeys.workspace), "geo"));
		}
		JaxbUtil.save(new File("../doc/src/main/resources/code.geojsf/geoserver/jaxb/datastores.xml"), dataStores, true);
		JaxbUtil.info(dataStores);
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		
		CliGeoServerDataStoreManager test = new CliGeoServerDataStoreManager(config,rest);
		test.read();
//		test.documentation();
	}
}