package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.PolygonSymbolizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPolygonSymbolizerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPolygonSymbolizerFactory.class);
	public static final long serialVersionUID=1;
	
	public static PolygonSymbolizer build()
	{
		PolygonSymbolizer xml = new PolygonSymbolizer();
		return xml;
	}
}