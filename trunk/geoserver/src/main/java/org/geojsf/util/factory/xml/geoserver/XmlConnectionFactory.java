package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlConnectionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlConnectionFactory.class);
	
	public static final long serialVersionUID=1;
		
	public static void transform(Element connectionParameters)
	{
		connectionParameters.setName("connection");
		
		XmlHostFactory.transform(connectionParameters);
		XmlDatabaseFactory.transform(connectionParameters);
	}
}
