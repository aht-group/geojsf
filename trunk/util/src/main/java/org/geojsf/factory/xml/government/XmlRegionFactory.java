package org.geojsf.factory.xml.government;

import java.io.Serializable;

import org.geojsf.model.xml.government.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRegionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRegionFactory.class);
	public static final long serialVersionUID=1;
	
	public static Region create(String code)
	{
        Region xml = new Region();
		xml.setCode(code);
		return xml;
	}
}
