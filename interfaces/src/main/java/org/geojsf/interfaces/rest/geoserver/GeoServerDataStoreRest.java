package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspace;

public interface GeoServerDataStoreRest
{	
	DataStores getDataStores(String workspace) throws IOException;
	DataStore dataStore(String workspace, String dataStore) throws IOException;
	void createDataStore(DataStore datastore, Workspace workspace) throws IOException;
}