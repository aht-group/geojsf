package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.jdom2.Document;

public interface GeoServerStyleRest
{	
	Document getStyle(String workspace, String style) throws IOException;
	void createStyle(String workspace, Document style) throws IOException;
}