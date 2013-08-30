package org.geojsf.interfaces.rest;

import java.io.IOException;

import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Styles;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jdom2.Document;

public interface GeoServerRest
{	
	Styles styles() throws IOException;
	Styles styles(String workspace) throws IOException;
	
	Document style(String name) throws IOException;
	Document style(String workspace, String name) throws IOException;
	
	void deleteStyle(String name) throws IOException;
	void deleteStyle(String workspace, String name) throws IOException;
	
//	void updateStyle(Document doc) throws IOException;
	void updateStyle(String workspace, Document doc) throws IOException;
	
	//WORKSPACE
	Workspaces getWorkspaces() throws IOException;
	Workspace getWorkspace(String workspaceName) throws IOException;
	void createWorkspace(Workspace workspace) throws IOException;
	
	//DATASTORE
	DataStores getDataStores(String workspace) throws IOException;
	DataStore dataStore(String workspace, String dataStore) throws IOException;
	void createDataStore(DataStore datastore, Workspace workspace) throws IOException;
}