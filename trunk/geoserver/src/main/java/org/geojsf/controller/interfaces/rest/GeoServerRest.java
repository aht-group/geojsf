package org.geojsf.controller.interfaces.rest;

import java.io.IOException;

import org.geojsf.xml.geoserver.Styles;
import org.jdom.Document;

public interface GeoServerRest
{	
	Styles styles() throws IOException;
	Styles styles(String workspace) throws IOException;
	
	Document style(String name) throws IOException;
	Document style(String workspace, String name) throws IOException;
	
	void deleteStyle(String name) throws IOException;
	void deleteStyle(String workspace, String name) throws IOException;
	
//	void updateStyle(Document doc) throws IOException;
	void updateStyle(String workspace, Document doc) throws IOException;
}
