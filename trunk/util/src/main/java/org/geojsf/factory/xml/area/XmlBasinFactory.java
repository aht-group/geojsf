package org.geojsf.factory.xml.area;

import java.io.Serializable;

import org.geojsf.model.xml.area.Basin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBasinFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlBasinFactory.class);
	public static final long serialVersionUID=1;
	
	public static Basin build()
	{
		Basin xml = new Basin();

		return xml;
	}
}
