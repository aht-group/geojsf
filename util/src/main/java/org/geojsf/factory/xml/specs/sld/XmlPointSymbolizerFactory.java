package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.PointSymbolizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPointSymbolizerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPointSymbolizerFactory.class);
	public static final long serialVersionUID=1;
	
	public static PointSymbolizer build()
	{
		PointSymbolizer xml = new PointSymbolizer();

		return xml;
	}
}