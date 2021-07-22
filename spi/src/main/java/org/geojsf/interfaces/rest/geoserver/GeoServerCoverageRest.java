package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.CoverageStore;
import org.geojsf.model.xml.geoserver.CoverageStores;
import org.geojsf.model.xml.geoserver.Coverages;
import org.geojsf.model.xml.geoserver.Workspace;
import org.jdom2.Document;

public interface GeoServerCoverageRest
{	
	CoverageStores getCoverageStores(String workspace) throws IOException;
	CoverageStore coverageStore(String workspace, String coverageStore) throws IOException;
	void createCoverageStore(Workspace ws,CoverageStore cs) throws IOException;
	
	Coverages getCoverages(String workSpace, String coverageStore) throws IOException;
	Document getCoverage(String workSpace, String coverageStore, String coverage) throws IOException;
	void createCoverage(String workSpace, String coverageStore, Document coverage) throws IOException;
}