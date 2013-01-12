package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDatabaseFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDatabaseFactory.class);
	public static final long serialVersionUID=1;
		
	public static final Namespace ns = Namespace.getNamespace("n","http://exlp.sf.net/net");
	
	@SuppressWarnings("unchecked")
	public static void transform(Element connectionParameters)
	{
		logger.warn("transform");
		String user = null;
		String port = null;
		
		List<Object> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
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
		if(user!=null )
		{
			Element eDatabase = new Element("database",ns);
			eDatabase.setAttribute("user", user);
			connectionParameters.addContent(eDatabase);
		}
		

	}
}
