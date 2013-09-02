package org.geojsf.factory.xml.exlp;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public static String keyHost = "host";
	public static String keyPort = "port";
	
	public static void transform(Element eConnectionParameters)
	{
		Element eHost = new Element("host",ns);
		
		List<Element> listDetach = new ArrayList<Element>();
		List<Element> list = eConnectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals(keyHost)){eHost.setAttribute("name", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyPort)){eHost.setAttribute(keyPort, e.getValue());listDetach.add(e);}
			}
		}
		for(Element e : listDetach){e.detach();}
		eConnectionParameters.addContent(eHost);
	}
}
