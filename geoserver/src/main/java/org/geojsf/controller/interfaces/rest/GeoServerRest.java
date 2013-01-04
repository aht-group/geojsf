package org.geojsf.controller.interfaces.rest;

import java.io.IOException;

import org.geojsf.xml.geoserver.Styles;
import org.jdom.Document;

public interface GeoServerRest
{	
	Styles styles() throws IOException;
	Styles styles(String prefixFilter) throws IOException;
	
	Document style(String code) throws IOException;
}
