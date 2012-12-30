package net.sf.geojsf.controller.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.xml.geoserver.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataStoreFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataStoreFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static DataStore build(String name, String description)
	{
		DataStore xml = new DataStore();
		
		xml.setName(name);
		xml.setDescription(description);
				
		return xml;
	}
}
