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
	
	@SuppressWarnings("unused")
	public static void transform(Element connectionParameters)
	{
		String user = null;
		String port = null;
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Object o : list)
		{
			if(o instanceof Element)
			{
				Element e = (Element)o;
				if(e.getAttribute("key") != null)
				{
					if(e.getAttributeValue("key").equals("user")){user = e.getValue();}
					if(e.getAttributeValue("key").equals("port")){port = e.getValue();}
				}
			}
		}
		if(user!=null)
		{
			Element eDatabase = new Element("database",ns);
			eDatabase.setAttribute("user", user);
			connectionParameters.addContent(eDatabase);
		}
	}
}