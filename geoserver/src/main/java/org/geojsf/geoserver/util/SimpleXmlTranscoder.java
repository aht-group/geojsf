package org.geojsf.geoserver.util;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleXmlTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(SimpleXmlTranscoder.class);

	public static final Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
	
	public static void name(Element e)
	{
		elementToAttribute(e, "name");
	}
	
	public static void description(Element e)
	{
		elementToAttribute(e, "description");
	}
	
	public static void elementToAttribute(Element e, String name)
	{
		Element child = e.getChild(name,ns);
		if(child!=null)
		{
			e.setAttribute(name, child.getText());
		}
	}
}