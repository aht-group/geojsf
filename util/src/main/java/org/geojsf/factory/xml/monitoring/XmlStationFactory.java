package org.geojsf.factory.xml.monitoring;

import java.io.Serializable;

import org.geojsf.model.xml.monitoring.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStationFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlStationFactory.class);
	public static final long serialVersionUID=1;
	
	public static Station build(String code, String label)
	{
		Station xml = new Station();
		xml.setCode(code);
		
		return xml;
	}
}
