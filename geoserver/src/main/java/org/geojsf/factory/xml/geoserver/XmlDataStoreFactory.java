package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.xml.identity.User;
import net.sf.exlp.xml.net.Host;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.Postgis;
import org.geojsf.xml.geoserver.Workspace;
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
		
		Element eWorkspace = dataStore.getChild("workspace", SimpleXmlTranscoder.ns);
		if(eWorkspace!=null){XmlWorkspaceFactory.transform(eWorkspace);}
		
		Element eConnectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		if(eConnectionParameters!=null)
		{
			logger.info(dataStore.getAttributeValue("type"));
			XmlConnectionFactory.transform(eConnectionParameters);
		}
	}
	
	public static Element build(DataStore ds, Workspace ws)
	{
		Element eDs = new Element("dataStore");
		
		Element eName = new Element("name");
		eName.setText(ds.getName());
		eDs.addContent(eName);
		
		Element eDescription = new Element("description");
		eDescription.setText(ds.getDescription());
		eDs.addContent(eDescription);

		Element eType = new Element("type");
		eType.setText(ds.getType());
		eDs.addContent(eType);
		
		Element eEnabled = new Element("enabled");
		eEnabled.setText(""+ds.isEnabled());
		eDs.addContent(eEnabled);
		
		eDs.addContent(XmlWorkspaceFactory.build(ws));
		
		Element eConnectionParameters = new Element("connectionParameters");
		if(ds.isSetPostgis()){addPostgisConnectionParameter(eConnectionParameters,ds.getPostgis());}
		eDs.addContent(eConnectionParameters);
		
		return eDs;
	}
		
	private static void addPostgisConnectionParameter(Element eConnectionParameters, Postgis postgis)
	{
		//Database
		eConnectionParameters.addContent(buildKey("dbtype","postgis"));
		eConnectionParameters.addContent(buildKey(XmlHostFactory.keyHost,postgis.getConnection().getHost().getName()));
		eConnectionParameters.addContent(buildKey(XmlHostFactory.keyPort,postgis.getConnection().getHost().getPort()));
		eConnectionParameters.addContent(buildKey(XmlDatabaseFactory.keyUser,postgis.getConnection().getDatabase().getUser()));
		eConnectionParameters.addContent(buildKey(XmlDatabaseFactory.keyDatabase,postgis.getConnection().getDatabase().getDatabase()));
		eConnectionParameters.addContent(buildKey(XmlDatabaseFactory.keySchema,postgis.getConnection().getDatabase().getSchema()));
		eConnectionParameters.addContent(buildKey(XmlDatabaseFactory.keyType,postgis.getConnection().getDatabase().getType()));
		
		//Connection
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyMin,postgis.getConnection().getMin()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyMax,postgis.getConnection().getMax()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyTimeout,postgis.getConnection().getTimeout()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyFetchSize,postgis.getConnection().getFetchSize()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyPrepared,postgis.getConnection().isPreparedStatements()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyMaxPrepared,postgis.getConnection().getMaxPreparedStatements()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyValidate,postgis.getConnection().isValidate()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyBbox,postgis.getConnection().isLooseBbox()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyEncode,postgis.getConnection().isEncodeFunctions()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyExpose,postgis.getConnection().isExposePrimaryKeys()));
		eConnectionParameters.addContent(buildKey(XmlConnectionFactory.keyExtends,postgis.getConnection().isEstimatedExtends()));
	}
	
	private static Element buildKey(String key, boolean value){return buildKey(key,""+value);}
	private static Element buildKey(String key, int value){return buildKey(key,""+value);}
	private static Element buildKey(String key, String value)
	{
		Element eKey = new Element("entry");
		eKey.setAttribute("key", key);
		eKey.setText(value);
		return eKey;
	}
}