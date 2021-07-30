package org.geojsf.geoserver.manager;

import java.io.IOException;

import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.interfaces.rest.geoserver.GeoServerFeatureTypeRest;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.FeatureType;
import org.geojsf.model.xml.geoserver.FeatureTypes;
import org.geojsf.model.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerFeatureTypeManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerFeatureTypeManager.class);
	
	public static String xml = "featureTypes.xml";
	
	private GeoServerFeatureTypeRest rest;
	private GeoServerDataStoreManager dsManager;

	public GeoServerFeatureTypeManager(GeoServerRest rest)
	{
		this.rest=rest;
		dsManager = new GeoServerDataStoreManager(rest);
	}
	
	public FeatureTypes getFeatureTypes(Workspace ws) throws IOException
	{
		logger.info("getFeatureTypes "+FeatureTypes.class.getSimpleName());
		FeatureTypes result = new FeatureTypes();
		
		for(DataStore ds : dsManager.getDataStores(ws).getDataStore())
		{
			logger.info("getFeatureTypes "+FeatureTypes.class.getSimpleName()+ " "+ds.getName());
			FeatureTypes fts = rest.getFeatureTypes(ws.getName(), ds.getName());
			for(FeatureType ft : fts.getFeatureType())
			{
				ft = rest.getFeatureType(ws.getName(), ds.getName(), ft.getName());
				result.getFeatureType().add(ft);
			}		}
		
		return result;
	}
	
	public Document exportFeatureType(String workSpace, String coverageStore, String coverage) throws IOException
	{
		return rest.exportFeatureType(workSpace, coverageStore, coverage);
	}
	
	public void createFeatureType(String workSpace, String dataStore, Document featureType) throws IOException
	{
		rest.createFeatureType(workSpace,dataStore,featureType);
	}
}