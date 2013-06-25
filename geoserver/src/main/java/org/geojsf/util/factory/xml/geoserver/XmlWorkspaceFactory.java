package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWorkspaceFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlWorkspaceFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element workspace)
	{
		SimpleXmlTranscoder.name(workspace);
	}
}
