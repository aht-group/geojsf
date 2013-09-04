package org.geojsf.geoserver.manager;

import java.io.IOException;

import org.geojsf.interfaces.rest.geoserver.GeoServerLayerRest;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerLayerManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerLayerManager.class);
	
	public static String xml = "coveragestores.xml";
	private GeoServerLayerRest rest;

	public GeoServerLayerManager(GeoServerLayerRest rest)
	{
		this.rest=rest;
	}
	
	public Layers getLayer(Workspace ws) throws IOException
	{
		Layers allLayers = rest.getLayers(ws.getName());
		
		return allLayers;
	}
}