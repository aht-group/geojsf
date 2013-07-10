package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.util.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspace;
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
//ahtutils.highlight:init
		GeoServerDataStoreManager dataStoreManager = new GeoServerDataStoreManager(rest);
//ahtutils.highlight:init
		return dataStoreManager;
	}
	
	public void basic() throws IOException
	{
		Workspace ws = XmlWorkspaceFactory.build("lis");
		DataStores dataStores;
		boolean available;
//ahtutils.highlight:basic		
		dataStores = dataStoreManager.getDataStores(ws);
//ahtutils.highlight:basic
		JaxbUtil.info(dataStores);
	}

	public void read() throws IOException
	{
		Workspace ws = XmlWorkspaceFactory.build("lis");
		DataStore ds = XmlDataStoreFactory.build("lcbc-public");
//ahtutils.highlight:read
		boolean available = dataStoreManager.isAvailable(ws, ds);
		DataStores dataStores = dataStoreManager.getDataStores(ws);
		
//ahtutils.highlight:read
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