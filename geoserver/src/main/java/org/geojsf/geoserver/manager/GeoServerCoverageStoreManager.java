package org.geojsf.geoserver.manager;

import java.io.IOException;

import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerCoverageStoreManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerCoverageStoreManager.class);
	
	public static String xml = "coveragestores.xml";
	private GeoServerRest rest;

	public GeoServerCoverageStoreManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public CoverageStores getCoverageStores(Workspace ws) throws IOException
	{
		CoverageStores coverageStores = rest.getCoverageStores(ws.getName());
		
		return coverageStores;
	}
	
	
}