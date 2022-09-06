package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.geojsf.model.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerDataStoreManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerDataStoreManager.class);
	
	public static String dsXml = "datastores.xml";
	
	private GeoServerRest rest;

	public GeoServerDataStoreManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public DataStores getDataStores(Workspace ws) throws IOException
	{
		DataStores dataStores = new DataStores();
		for(DataStore ds : rest.getDataStores(ws.getName()).getDataStore())
		{
			dataStores.getDataStore().add(ds);
		}
		return dataStores;
	}
	
	public boolean isAvailable(Workspace ws, DataStore ds) throws IOException
	{
		Set<String> set = new HashSet<String>();
		for(DataStore existing : getDataStores(ws).getDataStore())
		{
			set.add(existing.getName());
		}
		return set.contains(ds.getName());
	}
	
	public void createDataStore(Workspace ws, DataStore ds) throws IOException
	{
		rest.createDataStore(ds, ws);
	}
}