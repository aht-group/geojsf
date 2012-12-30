package net.sf.geojsf.controller.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.xml.net.Host;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.GeoServerConfig;
import org.geojsf.xml.geoserver.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataStoreFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataStoreFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static DataStore build(Configuration config)
	{
		DataStore ds = build(config.getString(GeoServerConfig.dsName),
				config.getString(GeoServerConfig.dsDescription));
		
		Host host = new Host();
		
		return ds;
	}
	
	public static DataStore build(String name, String description)
	{
		DataStore xml = new DataStore();
		
		xml.setName(name);
		xml.setDescription(description);
				
		return xml;
	}
}
