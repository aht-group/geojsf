package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.FeatureTypeStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeStyleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeStyleFactory.class);
	public static final long serialVersionUID=1;
	
	public static FeatureTypeStyle build()
	{
		FeatureTypeStyle xml = new FeatureTypeStyle();
		return xml;
	}
}