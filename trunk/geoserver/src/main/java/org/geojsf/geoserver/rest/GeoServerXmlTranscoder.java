package org.geojsf.geoserver.rest;

import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerXmlTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerXmlTranscoder.class);

	private static final Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");

	public static void dataStore(Element e) throws IOException
	{
		name(e);
		description(e);
	}
	
	private static void name(Element e)
	{
		Element name = e.getChild("name",ns);
		e.setAttribute("name", name.getText());
	}
	
	private static void description(Element e)
	{
		Element child = e.getChild("description",ns);
		if(child!=null)
		{
			e.setAttribute("description", child.getText());
		}
	}
}