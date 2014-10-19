package org.geojsf.util.wfs;

import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWfsFeatureIdResolver
{
	final static Logger logger = LoggerFactory.getLogger(DefaultWfsFeatureIdResolver.class);
	
	@SuppressWarnings("unused")
	private String layerCode;
	
	public DefaultWfsFeatureIdResolver(String layerCode)
	{
		this.layerCode=layerCode;
	}
	
	public long getId(Element e)
	{
		String s = e.getAttributeValue("fid");
		Long id = new Long(s.substring(s.lastIndexOf(".")+1));
		return id;
	}
}