package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.xml.identity.User;
import net.sf.exlp.xml.net.Host;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerConfigKeys;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.xml.geoserver.DataStore;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataStoreFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataStoreFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static DataStore build(Configuration config)
	{
		DataStore ds = build(config.getString(GeoServerConfigKeys.dsName),
				config.getString(GeoServerConfigKeys.dsDescription),
				config.getString(GeoServerConfigKeys.dsDatabase),
				config.getString(GeoServerConfigKeys.dsSchema));
		
		Host host = new Host();
		host.setName(config.getString(GeoServerConfigKeys.dsHost));
		host.setPort(config.getInt(GeoServerConfigKeys.dsPort));
//		ds.setHost(host);
		
		User user = new User();
		user.setAccount(config.getString(GeoServerConfigKeys.dsUser));
		user.setPassword(config.getString(GeoServerConfigKeys.dsPassword));
//		ds.setUser(user);
		
		return ds;
	}
	
	public static DataStore build(String name){return build(name,null,null,null);}
	public static DataStore build(String name, String description, String database, String schema)
	{
		DataStore xml = new DataStore();
		
		xml.setName(name);
		/*		xml.setDescription(description);
		xml.setDatabase(database);
		xml.setSchema(schema);
*/				
		return xml;
	}
	
	public static void transform(Element dataStore)
	{
		SimpleXmlTranscoder.name(dataStore);
		SimpleXmlTranscoder.description(dataStore);
		SimpleXmlTranscoder.elementToAttribute(dataStore, "type");
		SimpleXmlTranscoder.elementToAttribute(dataStore, "enabled");
		
		Object oWorkspace = dataStore.getChild("workspace", SimpleXmlTranscoder.ns);
		if(oWorkspace!=null && (oWorkspace instanceof Element)){XmlWorkspaceFactory.transform((Element)oWorkspace);}
		
		Object oConnectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		if(oConnectionParameters!=null && (oConnectionParameters instanceof Element)){XmlConnectionFactory.transform((Element)oConnectionParameters);}
	}
}
