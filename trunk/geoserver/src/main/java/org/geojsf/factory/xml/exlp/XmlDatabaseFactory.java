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
		Element eDatabase = new Element("database",ns);
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals("user")){eDatabase.setAttribute("user", e.getValue());}
				if(e.getAttributeValue("key").equals("database")){eDatabase.setAttribute("database", e.getValue());}
				if(e.getAttributeValue("key").equals("schema")){eDatabase.setAttribute("schema", e.getValue());}
			}
		}
		connectionParameters.addContent(eDatabase);
	}
}
