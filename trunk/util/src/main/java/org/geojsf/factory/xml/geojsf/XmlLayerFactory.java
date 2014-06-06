package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.xml.geojsf.Layer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLayerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static Layer build(String code)
	{
		Layer xml = new Layer();
		xml.setCode(code);
		return xml;
	}
}
