package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.OnlineResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOnlineResourceFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlOnlineResourceFactory.class);
	public static final long serialVersionUID=1;
	
	public static OnlineResource build(String href)
	{
		OnlineResource xml = new OnlineResource();

		return xml;
	}
}