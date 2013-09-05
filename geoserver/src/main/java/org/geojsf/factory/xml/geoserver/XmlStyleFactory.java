package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStyleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlStyleFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element eLayer)
	{
		logger.trace("Transforming ... ");
		if(eLayer.getName().equals("defaultStyle")){eLayer.setName("style");}
		SimpleXmlTranscoder.name(eLayer);
	}	
}