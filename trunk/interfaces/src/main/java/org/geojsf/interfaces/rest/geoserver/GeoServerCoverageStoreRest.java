package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;

public interface GeoServerCoverageStoreRest
{	
	CoverageStores getCoverageStores(String workspace) throws IOException;
	CoverageStore coverageStore(String workspace, String coverageStore) throws IOException;
}