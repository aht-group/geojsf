package org.geojsf.factory.xml.exlp;

import java.io.Serializable;
import java.util.List;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHostFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlHostFactory.class);
	public static final long serialVersionUID=1;
		
	public static final Namespace ns = Namespace.getNamespace("n","http://exlp.sf.net/net");
	
	public static void transform(Element eConnectionParameters)
	{
		Element eHost = new Element("host",ns);
		
		List<Element> list = eConnectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals("host")){eHost.setAttribute("name", e.getValue());}
				if(e.getAttributeValue("key").equals("port")){eHost.setAttribute("port", e.getValue());}
			}
		}
		eConnectionParameters.addContent(eHost);
	}
}
