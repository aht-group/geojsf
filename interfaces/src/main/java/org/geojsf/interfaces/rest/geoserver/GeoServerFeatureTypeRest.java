package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.FeatureType;

public interface GeoServerFeatureTypeRest
{	
	FeatureType getFeatureType(String ws, String ds, String ft) throws IOException;
}