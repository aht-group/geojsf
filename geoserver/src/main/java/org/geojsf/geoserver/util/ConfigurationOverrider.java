package org.geojsf.geoserver.util;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationOverrider
{
	final static Logger logger = LoggerFactory.getLogger(ConfigurationOverrider.class);

	public static final Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
	
	private Configuration config;
	
	public ConfigurationOverrider(Configuration config)
	{
		this.config = config;
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
				logger.info("Key:"+key+" "+value);
				dataStore.getShapeDir().setUrl(value);
			}
			catch (NoSuchElementException e){}
		}
		if(dataStore.isSetPostgis())
		{
			try
			{
				String key = getKey(dataStore);
				String value = config.getString(key);
				logger.info("Key:"+key+" "+value);
				dataStore.getPostgis().getConnection().getDatabase().setPassword(value);
			}
			catch (NoSuchElementException e){}
		}
	}
	
	private String getKey(DataStore dataStore)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("geoserver.");
		sb.append(dataStore.getWorkspace().getName());
		sb.append(".");
		sb.append(dataStore.getName());
		
		String key = sb.toString();
		key = key.replace("-", "");
		
		return key;
	}
}