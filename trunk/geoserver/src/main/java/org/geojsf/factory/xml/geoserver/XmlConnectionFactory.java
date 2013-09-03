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
	
	public static String typePostGis = "PostGIS";
	public static String typeShapeDir = "Directory of spatial files (shapefiles)";
	
	public static void transform(Element dataStore)
	{
		logger.trace("Transforming ... ");
		Element connectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		if(connectionParameters!=null)
		{
			logger.info(dataStore.getAttributeValue("type"));
			if(dataStore.getAttributeValue("type").equals(typePostGis)) {XmlPostgisFactory.transform(dataStore);}
			if(dataStore.getAttributeValue("type").equals(typeShapeDir)) {XmlShapeDirFactory.transform(dataStore);}
		}
	}
}
