package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlConnectionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlConnectionFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static String keyMin = "min connections";
	public static String keyMax = "max connections";
	public static String keyTimeout = "Connection timeout";
	public static String keyFetchSize = "fetch size";
	public static String keyMaxPrepared = "Max open prepared statements";
	public static String keyPrepared = "preparedStatements";
	public static String keyValidate = "validate connections";
	public static String keyBbox = "Loose bbox";
	public static String keyEncode = "encode functions";
	public static String keyExpose = "Expose primary keys";
	public static String keyExtends = "Estimated extends";
	
	public static void transform(Element dataStore)
	{
		Element connectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		if(connectionParameters!=null)
		{
			logger.trace(dataStore.getAttributeValue("type"));
			if(dataStore.getAttributeValue("type").equals("PostGIS")) {XmlPostgisFactory.transform(dataStore);}
		}
	}
}
