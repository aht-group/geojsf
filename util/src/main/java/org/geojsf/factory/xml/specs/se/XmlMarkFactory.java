package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.Mark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMarkFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMarkFactory.class);
	public static final long serialVersionUID=1;
	
	public static Mark build(String wkn)
	{
		Mark xml = new Mark();
		xml.setWellKnownName(XmlWellKnownNameFactory.build(wkn));
		return xml;
	}
}