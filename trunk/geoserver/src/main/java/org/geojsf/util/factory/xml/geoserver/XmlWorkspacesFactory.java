package org.geojsf.util.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWorkspacesFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlWorkspacesFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element workspace)
	{
		for(Object o : workspace.getChildren("workspace",SimpleXmlTranscoder.ns))
		{
			if(o instanceof Element)
			{
				XmlWorkspaceFactory.transform((Element)o);
			}
		}
	}
}
