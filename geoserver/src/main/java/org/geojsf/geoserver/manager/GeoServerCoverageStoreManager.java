package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.interfaces.rest.geoserver.GeoServerCoverageStoreRest;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerCoverageStoreManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerCoverageStoreManager.class);
	
	public static String xml = "coveragestores.xml";
	private GeoServerCoverageStoreRest rest;

	public GeoServerCoverageStoreManager(GeoServerCoverageStoreRest rest)
	{
		this.rest=rest;
	}
	
	public CoverageStores getCoverageStores(Workspace ws) throws IOException
	{
		CoverageStores coverageStores = rest.getCoverageStores(ws.getName());
		
		return coverageStores;
	}
	
	public boolean isAvailable(Workspace ws, CoverageStore ds) throws IOException
	{
		Set<String> set = new HashSet<String>();
		for(CoverageStore existing : getCoverageStores(ws).getCoverageStore())
		{
			set.add(existing.getName());
		}
		return set.contains(ds.getName());
	}
	
	public void createCoverageStore(Workspace ws, CoverageStore cs) throws IOException
	{
		rest.createCoverageStore(ws, cs);
	}
}