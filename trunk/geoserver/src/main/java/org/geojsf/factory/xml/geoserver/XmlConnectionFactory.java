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
	
	public static void transform(Element connectionParameters)
	{
		connectionParameters.setName("connection");
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		List<Element> listDetach = new ArrayList<Element>();
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals(keyTimeout)){connectionParameters.setAttribute("timeout", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyMin)){connectionParameters.setAttribute("min", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyMax)){connectionParameters.setAttribute("max", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyFetchSize)){connectionParameters.setAttribute("fetchSize", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyMaxPrepared)){connectionParameters.setAttribute("maxPreparedStatements", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyPrepared)){connectionParameters.setAttribute("preparedStatements", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyValidate)){connectionParameters.setAttribute("validate", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyBbox)){connectionParameters.setAttribute("looseBbox", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyEncode)){connectionParameters.setAttribute("encodeFunctions", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyExpose)){connectionParameters.setAttribute("exposePrimaryKeys", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyExtends)){connectionParameters.setAttribute("estimatedExtends", e.getValue());listDetach.add(e);}
			}
		}
		for(Element e : listDetach){e.detach();}
		
		XmlHostFactory.transform(connectionParameters);
		XmlDatabaseFactory.transform(connectionParameters);
	}
}
