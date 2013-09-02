package org.geojsf.factory.xml.exlp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDatabaseFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDatabaseFactory.class);
	public static final long serialVersionUID=1;
		
	public static final Namespace ns = Namespace.getNamespace("n","http://exlp.sf.net/net");
	
	public static String keyUser = "user";
	public static String keyDatabase = "database";
	public static String keySchema = "schema";
	public static String keyType = "dbtype";
	
	public static void transform(Element connectionParameters)
	{
		Element eDatabase = new Element("database",ns);
		
		List<Element> listDetach = new ArrayList<Element>();
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals(keyUser)){eDatabase.setAttribute(keyUser, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyDatabase)){eDatabase.setAttribute(keyDatabase, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keySchema)){eDatabase.setAttribute(keySchema, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyType)){eDatabase.setAttribute("type", e.getValue());listDetach.add(e);}
			}
		}
		for(Element e : listDetach){e.detach();}
		connectionParameters.addContent(eDatabase);
	}
}
