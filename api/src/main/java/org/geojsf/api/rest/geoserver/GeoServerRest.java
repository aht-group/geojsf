package org.geojsf.api.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.Styles;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.model.xml.geoserver.Workspaces;
import org.jdom2.Document;

public interface GeoServerRest extends GeoServerDataStoreRest,
										GeoServerCoverageRest,
										GeoServerLayerRest,
										GeoServerFeatureTypeRest,
										GeoServerStyleRest
{	
	Styles styles() throws IOException;
	Styles styles(String workspace) throws IOException;
	
	Document style(String name) throws IOException;
	Document style(String workspace, String name) throws IOException;
	
	void deleteStyle(String name) throws IOException;
	void deleteStyle(String workspace, String name) throws IOException;
	
//	void updateStyle(Document doc) throws IOException;
//	void updateStyle(String workspace, Document doc) throws IOException;
	
	//WORKSPACE
	Workspaces getWorkspaces() throws IOException;
	Workspace getWorkspace(String workspaceName) throws IOException;
	void createWorkspace(Workspace workspace) throws IOException;
}