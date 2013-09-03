package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPostgisFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPostgisFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element dataStore)
	{
		Element ePostgis = new Element("postgis",SimpleXmlTranscoder.ns);
		dataStore.addContent(ePostgis);
		
		Element connectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		connectionParameters.detach();
		ePostgis.addContent(connectionParameters);
		
		connectionParameters.setName("connection");
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		List<Element> listDetach = new ArrayList<Element>();
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyTimeout)){connectionParameters.setAttribute("timeout", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyMin)){connectionParameters.setAttribute("min", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyMax)){connectionParameters.setAttribute("max", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyFetchSize)){connectionParameters.setAttribute("fetchSize", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyMaxPrepared)){connectionParameters.setAttribute("maxPreparedStatements", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyPrepared)){connectionParameters.setAttribute("preparedStatements", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyValidate)){connectionParameters.setAttribute("validate", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyBbox)){connectionParameters.setAttribute("looseBbox", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyEncode)){connectionParameters.setAttribute("encodeFunctions", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyExpose)){connectionParameters.setAttribute("exposePrimaryKeys", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(XmlConnectionFactory.keyExtends)){connectionParameters.setAttribute("estimatedExtends", e.getValue());listDetach.add(e);}
			}
		}
		for(Element e : listDetach){e.detach();}
		
		XmlHostFactory.transform(connectionParameters);
		XmlDatabaseFactory.transform(connectionParameters);
	}
}
