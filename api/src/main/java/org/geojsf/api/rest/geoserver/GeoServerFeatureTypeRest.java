package org.geojsf.api.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.FeatureType;
import org.geojsf.model.xml.geoserver.FeatureTypes;
import org.jdom2.Document;

public interface GeoServerFeatureTypeRest
{	
	FeatureTypes getFeatureTypes(String ws, String ds) throws IOException;
	FeatureType getFeatureType(String ws, String ds, String ft) throws IOException;
	
	Document exportFeatureType(String workSpace, String dataStore, String featureType) throws IOException;
	void createFeatureType(String workSpace, String dataStore, Document featureType) throws IOException;
}