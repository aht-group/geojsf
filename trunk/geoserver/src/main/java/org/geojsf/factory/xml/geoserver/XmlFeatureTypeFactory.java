package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeFactory.class);
	public static final long serialVersionUID=1;
	
	public static String eName = "featureType";
	
	public static void transform(Element dataStore)
	{
		logger.trace("Transforming ... ");
		SimpleXmlTranscoder.name(dataStore);
	
	}
}