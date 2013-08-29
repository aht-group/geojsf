package org.geojsf.factory.xml.exlp;

import java.io.Serializable;
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
	
	public static void transform(Element connectionParameters)
	{
		String user = null;
		String database = null;
		String schema = null;
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Object o : list)
		{
			if(o instanceof Element)
			{
				Element e = (Element)o;
				if(e.getAttribute("key") != null)
				{
					if(e.getAttributeValue("key").equals("user")){user = e.getValue();}
					if(e.getAttributeValue("key").equals("database")){database = e.getValue();}
					if(e.getAttributeValue("key").equals("schema")){schema = e.getValue();}
				}
			}
		}
		Element eDatabase = new Element("database",ns);
		if(user!=null){eDatabase.setAttribute("user", user);}
		if(database!=null){eDatabase.setAttribute("database", user);}
		if(schema!=null){eDatabase.setAttribute("schema", schema);}
		connectionParameters.addContent(eDatabase);
	}
}
