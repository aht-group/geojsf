package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.geojsf.model.xml.geoserver.Workspace;

public interface GeoServerDataStoreRest
{	
	DataStores getDataStores(String workspace) throws IOException;
	DataStore dataStore(String workspace, String dataStore) throws IOException;
	void createDataStore(DataStore datastore, Workspace workspace) throws IOException;
}