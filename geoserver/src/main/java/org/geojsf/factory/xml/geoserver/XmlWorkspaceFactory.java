package org.geojsf.factory.xml.geoserver;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Element;
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
    
	public static void transform(Element workspace)
	{
		SimpleXmlTranscoder.name(workspace);
	}
	
	public static Element build(Workspace workspace)
	{
		Element eName = new Element("name");
		eName.setText(workspace.getName());
		
		Element eWs = new Element("workspace");
		eWs.addContent(eName);
		
		return eWs;
	}
}