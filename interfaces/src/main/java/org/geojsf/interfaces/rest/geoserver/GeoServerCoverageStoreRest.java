package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Workspace;

public interface GeoServerCoverageStoreRest
{	
	CoverageStores getCoverageStores(String workspace) throws IOException;
	CoverageStore coverageStore(String workspace, String coverageStore) throws IOException;
	void createCoverageStore(Workspace ws,CoverageStore cs) throws IOException;
}