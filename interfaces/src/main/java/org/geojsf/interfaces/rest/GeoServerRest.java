package org.geojsf.interfaces.rest;

import java.io.IOException;

import org.geojsf.interfaces.rest.geoserver.GeoServerCoverageRest;
import org.geojsf.interfaces.rest.geoserver.GeoServerDataStoreRest;
import org.geojsf.interfaces.rest.geoserver.GeoServerFeatureTypeRest;
import org.geojsf.interfaces.rest.geoserver.GeoServerLayerRest;
import org.geojsf.xml.geoserver.Styles;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jdom2.Document;

public interface GeoServerRest extends GeoServerDataStoreRest,GeoServerCoverageRest,GeoServerLayerRest,GeoServerFeatureTypeRest
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
}