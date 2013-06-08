package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHostFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlHostFactory.class);
	public static final long serialVersionUID=1;
		
	public static final Namespace ns = Namespace.getNamespace("n","http://exlp.sf.net/net");
	
	@SuppressWarnings("unchecked")
	public static void transform(Element connectionParameters)
	{
		logger.warn("transform");
		String host = null;
		String port = null;
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Object o : list)
		{
			if(o instanceof Element)
			{
				Element e = (Element)o;
				if(e.getAttribute("key") != null)
				{
					if(e.getAttributeValue("key").equals("host")){host = e.getValue();}
					if(e.getAttributeValue("key").equals("port")){port = e.getValue();}
				}
			}
		}
		if(host!=null && port !=null)
		{
			Element eHost = new Element("host",ns);
			eHost.setAttribute("name", host);
			eHost.setAttribute("port", port);
			connectionParameters.addContent(eHost);
		}
	}
}
