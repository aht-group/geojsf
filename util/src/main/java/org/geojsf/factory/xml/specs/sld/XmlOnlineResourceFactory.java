package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.OnlineResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOnlineResourceFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlOnlineResourceFactory.class);
	public static final long serialVersionUID=1;
	
	public static OnlineResource build(String href)
	{
		OnlineResource xml = new OnlineResource();
		xml.setType("simple");
		xml.setHref(href);
		return xml;
	}
}