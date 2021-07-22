package org.geojsf.geoserver.util;

import java.io.File;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.geoserver.manager.GeoServerCoverageManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.model.xml.geoserver.CoverageStore;
import org.geojsf.model.xml.geoserver.CoverageStores;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationOverrider
{
	final static Logger logger = LoggerFactory.getLogger(ConfigurationOverrider.class);

	public static final String cfgKeyOverrideCoverageDir = "geoserver.override.shape";
	public static final Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
	
	private Configuration config;
	
	public ConfigurationOverrider(Configuration config)
	{
		this.config = config;
		
		if(config.containsKey(cfgKeyOverrideCoverageDir))
		{
			logger.info("Using "+cfgKeyOverrideCoverageDir+" "+config.getString(cfgKeyOverrideCoverageDir));
		}
	}
	
	public void overrideDataStores(DataStores dataStores)
	{
		for(DataStore ds : dataStores.getDataStore())
		{
			overrideDataStore(ds);
		}
	}
	
	private void overrideDataStore(DataStore dataStore)
	{
		if(dataStore.isSetShapeDir())
		{
			try
			{
				String key = getKey(dataStore);
				String value = config.getString(key);
				logger.warn("Key:"+key+" "+value);
				
				dataStore.getShapeDir().setUrl(value);
			}
			catch (NoSuchElementException e){}
		}
		if(dataStore.isSetPostgis())
		{
			String base = getKey(dataStore);
			String key;
			
			key = base+".password";
			try{dataStore.getPostgis().getConnection().getDatabase().setPassword(config.getString(key));}
			catch (NoSuchElementException e){logger.debug("Key "+key+" not found");}
			
			key = base+".user";
			try{dataStore.getPostgis().getConnection().getDatabase().setUser(config.getString(key));}
			catch (NoSuchElementException e){logger.debug("Key "+key+" not found");}
			
			key = base+".database";
			try{dataStore.getPostgis().getConnection().getDatabase().setDatabase(config.getString(key));}
			catch (NoSuchElementException e){logger.debug("Key "+key+" not found");}
			
			key = base+".host";
			try{dataStore.getPostgis().getConnection().getHost().setName(config.getString(key));}
			catch (NoSuchElementException e){logger.debug("Key "+key+" not found");}
		}
	}
	
	public void overrideCoverageStores(CoverageStores coverageStores)
	{
		for(CoverageStore cs : coverageStores.getCoverageStore())
		{
			overrideCoverageStore(cs);
		}
	}
	
	private void overrideCoverageStore(CoverageStore cs)
	{
		String key = getKey(cs);
		try
		{
			if(config.containsKey(key))
			{
				String value = config.getString(key);
				cs.setUrl(value);
				logger.warn("Individual Override "+key+" "+cs.getUrl());
			}
			else
			{
				if(cs.getUrl().startsWith(GeoServerCoverageManager.geoserverUrlPrefix))
				{
					StringBuffer sb = new StringBuffer();
					sb.append(config.getString(cfgKeyOverrideCoverageDir));
					sb.append(File.separator);
					sb.append(cs.getUrl().substring(GeoServerCoverageManager.geoserverUrlPrefix.length()));
					cs.setUrl(sb.toString());
					logger.warn("Directory Override "+key+" "+cs.getUrl());
				}
				else
				{
					logger.warn("Unhandled URL structure :"+cs.getUrl());
					logger.warn("Please contatct GeoJSF Developer");
				}
			}
			
			
			
			
			
			
		}
		catch (NoSuchElementException e)
		{
			logger.debug("Key "+key+" not found");
		}
	}
	
	private String getKey(DataStore dataStore)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("geoserver.datastore");
		sb.append(".").append(dataStore.getWorkspace().getName());
		sb.append(".");
		sb.append(dataStore.getName());
		
		String key = sb.toString();
		key = key.replace("-", ".");
		
		return key;
	}
	
	private String getKey(CoverageStore cs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("geoserver.coverages");
		sb.append(".").append(config.getString(GeoServerWorkspaceManager.cjgKeyWorkspace));
		sb.append(".").append(cs.getName());
		
		String key = sb.toString();
		key = key.replace("-", ".");
		
		return key;
	}
}