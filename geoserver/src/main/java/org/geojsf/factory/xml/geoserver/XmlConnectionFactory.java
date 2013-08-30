package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
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
	
	public static void transform(Element connectionParameters)
	{
		connectionParameters.setName("connection");
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Element e : list)
		{
				if(e.getAttribute("key") != null)
				{
					if(e.getAttributeValue("key").equals(keyTimeout)){connectionParameters.setAttribute("timeout", e.getValue());}
					if(e.getAttributeValue("key").equals(keyMin)){connectionParameters.setAttribute("max", e.getValue());}
					if(e.getAttributeValue("key").equals(keyMin)){connectionParameters.setAttribute("min", e.getValue());}
					if(e.getAttributeValue("key").equals(keyFetchSize)){connectionParameters.setAttribute("fetchSize", e.getValue());}
					if(e.getAttributeValue("key").equals(keyMaxPrepared)){connectionParameters.setAttribute("maxPreparedStatements", e.getValue());}
					if(e.getAttributeValue("key").equals(keyPrepared)){connectionParameters.setAttribute("preparedStatements", e.getValue());}
					if(e.getAttributeValue("key").equals(keyValidate)){connectionParameters.setAttribute("validate", e.getValue());}
/*
		<entry key="encode functions">false</entry> 
		<entry key="Loose bbox">true</entry>
        <entry key="Expose primary keys">false</entry>
        
        <entry key="Estimated extends">true</entry>					 
*/
				}
		}
		
		XmlHostFactory.transform(connectionParameters);
		XmlDatabaseFactory.transform(connectionParameters);
	}
}
