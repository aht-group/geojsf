package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerConfigKeys;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspaces;
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
//ahtutils.highlight:basic		
		DataStores dataStore = rest.dataStores("geo");
		JaxbUtil.info(dataStore);
//ahtutils.highlight:basic
	}

	public void manager() throws IOException
	{
//ahtutils.highlight:basic		
		DataStores dataStore = dataStoreManager.dataStores("geo");
		JaxbUtil.info(dataStore);
//ahtutils.highlight:basic
	}
	
	public void documentation() throws IOException
	{
		DataStores dataStores = rest.dataStores(config.getString(GeoServerConfigKeys.workspace));
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
		test.documentation();
	}
}