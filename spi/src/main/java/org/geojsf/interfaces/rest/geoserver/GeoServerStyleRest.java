package org.geojsf.interfaces.rest.geoserver;

import java.io.IOException;

import org.geojsf.model.xml.geoserver.Styles;
import org.jdom2.Document;

public interface GeoServerStyleRest
{	
	Styles getStyles(String workspace) throws IOException;
	Document getStyle(String workspace, String style) throws IOException;
	void createStyle(String workspace, Document style) throws IOException;
}