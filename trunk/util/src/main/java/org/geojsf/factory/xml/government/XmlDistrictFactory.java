package org.geojsf.factory.xml.government;

import java.io.Serializable;

import org.geojsf.xml.government.District;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDistrictFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDistrictFactory.class);
	public static final long serialVersionUID=1;
	
	public static District create(String code)
	{
        District xml = new District();
		xml.setCode(code);
		return xml;
	}
}
