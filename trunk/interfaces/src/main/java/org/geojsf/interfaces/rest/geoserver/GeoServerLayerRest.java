package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.xml.geoserver.Layers;

public interface GeoServerLayerRest
{	
	Layers getLayers(String workspace) throws IOException;
}