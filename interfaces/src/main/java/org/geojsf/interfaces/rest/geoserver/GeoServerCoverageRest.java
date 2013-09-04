package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Coverages;
import org.geojsf.xml.geoserver.Workspace;

public interface GeoServerCoverageRest
{	
	CoverageStores getCoverageStores(String workspace) throws IOException;
	CoverageStore coverageStore(String workspace, String coverageStore) throws IOException;
	void createCoverageStore(Workspace ws,CoverageStore cs) throws IOException;
	
	Coverages getCoverages(String workSpace, String coverageStore) throws IOException;
}