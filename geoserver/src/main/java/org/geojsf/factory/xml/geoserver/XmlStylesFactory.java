package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStylesFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlStylesFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element eLayer)
	{
		logger.trace("Transforming ... ");
		for(Element e : eLayer.getChildren("style",GeoServerRestWrapper.ns))
		{
			XmlStyleFactory.transform(e);
		}
	}	
}