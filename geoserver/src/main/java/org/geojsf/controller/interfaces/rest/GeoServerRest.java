package org.geojsf.controller.interfaces.rest;

import java.io.IOException;

import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Styles;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jdom.Document;

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
	
	Workspaces workspaces() throws IOException;
	void createWorkspace(Workspace workspace) throws IOException;
	
	//DATASTORE
	DataStores dataStores(String workspace) throws IOException;
	DataStore dataStore(String workspace, String dataStore) throws IOException;
}