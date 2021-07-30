package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.xml.identity.User;
import net.sf.exlp.xml.net.Host;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.Postgis;
import org.geojsf.model.xml.geoserver.ShapeDir;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.util.GeoServerConfigKeys;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataCoverageFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataCoverageFactory.class);
	
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
		logger.trace("Transforming ... ");
		SimpleXmlTranscoder.name(dataStore);
		SimpleXmlTranscoder.description(dataStore);
		SimpleXmlTranscoder.elementToAttribute(dataStore, "type");
		SimpleXmlTranscoder.elementToAttribute(dataStore, "enabled");
		
		Element eWorkspace = dataStore.getChild("workspace", SimpleXmlTranscoder.ns);
		if(eWorkspace!=null){XmlWorkspaceFactory.transform(eWorkspace);}
		
		XmlConnectionFactory.transform(dataStore);
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
		else if(ds.isSetShapeDir()){addShapeDirConnectionParameter(eConnectionParameters,ds.getShapeDir());}
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
		eConnectionParameters.addContent(buildKey(XmlDatabaseFactory.keyPwd,postgis.getConnection().getDatabase().getPassword()));
		
		//Connection
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyMin,postgis.getConnection().getMin()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyMax,postgis.getConnection().getMax()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyTimeout,postgis.getConnection().getTimeout()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyFetchSize,postgis.getConnection().getFetchSize()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyPrepared,postgis.getConnection().isPreparedStatements()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyMaxPrepared,postgis.getConnection().getMaxPreparedStatements()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyValidate,postgis.getConnection().isValidate()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyBbox,postgis.getConnection().isLooseBbox()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyEncode,postgis.getConnection().isEncodeFunctions()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyExpose,postgis.getConnection().isExposePrimaryKeys()));
		eConnectionParameters.addContent(buildKey(XmlPostgisFactory.keyExtends,postgis.getConnection().isEstimatedExtends()));
	}
	
	private static void addShapeDirConnectionParameter(Element eConnectionParameters, ShapeDir shapeDir)
	{
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyFiletype,"shapefile"));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyFstype,"shape"));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyTimezone,"Europe/Berlin"));
		
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyCharSet,shapeDir.getCharset()));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyMemoryBuffer,shapeDir.isMemoryBuffer()));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyMemoryMaps,shapeDir.isCacheMemoryMaps()));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyUrl,shapeDir.getUrl()));
		
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyCreateSpatialIndex,shapeDir.getSpatial().isCreateIndex()));
		eConnectionParameters.addContent(buildKey(XmlShapeDirFactory.keyEnableSpatialIndex,shapeDir.getSpatial().isEnableIndex()));
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
