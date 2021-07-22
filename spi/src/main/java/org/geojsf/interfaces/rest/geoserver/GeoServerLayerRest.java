package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.Layer;
import org.geojsf.model.xml.geoserver.Layers;

public interface GeoServerLayerRest
{	
	Layers allLayers() throws IOException;
	Layer getLayer(String layer) throws IOException;
	
	void updateLayer(Layer layer);
}