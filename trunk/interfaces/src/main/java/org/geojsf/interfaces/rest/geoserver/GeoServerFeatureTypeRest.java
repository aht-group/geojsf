package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.FeatureTypes;

public interface GeoServerFeatureTypeRest
{	
	FeatureTypes getFeatureTypes(String ws, String ds) throws IOException;
	FeatureType getFeatureType(String ws, String ds, String ft) throws IOException;
}