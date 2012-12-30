package net.sf.geojsf.controller.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.xml.identity.User;
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
				config.getString(GeoServerConfig.dsDescription),
				config.getString(GeoServerConfig.dsDatabase),
				config.getString(GeoServerConfig.dsSchema));
		
		Host host = new Host();
		host.setName(config.getString(GeoServerConfig.dsHost));
		host.setPort(config.getInt(GeoServerConfig.dsPort));
		ds.setHost(host);
		
		User user = new User();
		user.setAccount(config.getString(GeoServerConfig.dsUser));
		user.setPassword(config.getString(GeoServerConfig.dsPassword));
		ds.setUser(user);
		
		return ds;
	}
	
	public static DataStore build(String name, String description, String database, String schema)
	{
		DataStore xml = new DataStore();
		
		xml.setName(name);
		xml.setDescription(description);
		xml.setDatabase(database);
		xml.setSchema(schema);
				
		return xml;
	}
}
