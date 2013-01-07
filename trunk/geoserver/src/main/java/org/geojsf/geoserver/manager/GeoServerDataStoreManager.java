package org.geojsf.geoserver.manager;

import java.io.IOException;

import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerDataStoreManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerDataStoreManager.class);
	
	private GeoServerRest rest;

	public GeoServerDataStoreManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public DataStores dataStores(String workspace) throws IOException
	{
		DataStores dataStores = new DataStores();
		for(DataStore ds : rest.dataStores(workspace).getDataStore())
		{
			dataStores.getDataStore().add(ds);
		}
		
		return dataStores;
	}
	
}