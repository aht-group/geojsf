package org.geojsf.factory.xml.geoserver;

import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWorkspaceFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlWorkspaceFactory.class);
    
    public static Workspace build(String name)
    {
    	Workspace xml = new Workspace();
    	xml.setName(name);
    	return xml;
    }
}