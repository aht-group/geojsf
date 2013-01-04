package org.geojsf.controller.interfaces.rest;

import java.io.IOException;

import org.geojsf.xml.geoserver.Styles;

public interface GeoServerRest
{	
	Styles styles() throws IOException;
}
