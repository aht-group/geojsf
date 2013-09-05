package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.interfaces.rest.geoserver.GeoServerCoverageRest;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerCoverageManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerCoverageManager.class);
	
	public static String xml = "coveragestores.xml";
	private GeoServerCoverageRest rest;

	public GeoServerCoverageManager(GeoServerCoverageRest rest)
	{
		this.rest=rest;
	}
	
	public CoverageStores getCoverageStores(Workspace ws) throws IOException
	{
		CoverageStores result = new CoverageStores();
		for(CoverageStore cs : rest.getCoverageStores(ws.getName()).getCoverageStore())
		{
			cs = rest.coverageStore(ws.getName(), cs.getName());
			cs.setCoverages(rest.getCoverages(ws.getName(), cs.getName()));
			result.getCoverageStore().add(cs);
		}
		return result;
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
	
	public Document getCoverage(String workSpace, String coverageStore, String coverage) throws IOException
	{
		return rest.getCoverage(workSpace, coverageStore, coverage);
	}
	
	public void createCoverage(String workSpace, String coverageStore, Document coverage) throws IOException
	{
		rest.createCoverage(workSpace, coverageStore, coverage);
	}
}