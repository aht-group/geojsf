package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.FeatureTypes;
import org.jdom2.Document;

public interface GeoServerFeatureTypeRest
{	
	FeatureTypes getFeatureTypes(String ws, String ds) throws IOException;
	FeatureType getFeatureType(String ws, String ds, String ft) throws IOException;
	
	Document exportFeatureType(String workSpace, String coverageStore, String coverage) throws IOException;
}