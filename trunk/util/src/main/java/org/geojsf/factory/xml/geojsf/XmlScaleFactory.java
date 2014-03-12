package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.xml.geojsf.Scale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScaleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlScaleFactory.class);
	
	public static final long serialVersionUID=1;

	public static Scale build(int scale)
	{
		Scale xml = new Scale();
		xml.setValue(scale);
		return xml;
	}
}
