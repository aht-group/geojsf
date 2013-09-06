package org.geojsf.geoserver.manager;

import java.io.IOException;

import org.geojsf.interfaces.rest.GeoServerRest;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerStyleManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerStyleManager.class);
	
	private GeoServerRest rest;
	
	public GeoServerStyleManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public Document getStyle(String workspace, String style) throws IOException
	{
		return rest.getStyle(workspace, style);
	}
	
	public void createStyle(String workspace, Document style) throws IOException
	{
		rest.createStyle(workspace, style);
	}
}