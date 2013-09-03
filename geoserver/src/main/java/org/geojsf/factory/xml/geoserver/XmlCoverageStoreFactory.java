package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoverageStoreFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCoverageStoreFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element dataStore)
	{
		logger.trace("Transforming ... ");
		SimpleXmlTranscoder.name(dataStore);
		SimpleXmlTranscoder.description(dataStore);
		SimpleXmlTranscoder.elementToAttribute(dataStore, "type");
		SimpleXmlTranscoder.elementToAttribute(dataStore, "enabled");
		
		SimpleXmlTranscoder.elementToAttribute(dataStore, "url");
		
//		Element eWorkspace = dataStore.getChild("workspace", SimpleXmlTranscoder.ns);
//		if(eWorkspace!=null){XmlWorkspaceFactory.transform(eWorkspace);}
		
//		XmlConnectionFactory.transform(dataStore);
	}
	
	public static Element build(Workspace ws, CoverageStore cs)
	{
		Element eCs = new Element("coverageStore");
		
		Element eName = new Element("name");
		eName.setText(cs.getName());
		eCs.addContent(eName);
		
		Element eDescription = new Element("description");
		eDescription.setText(cs.getDescription());
		eCs.addContent(eDescription);

		Element eType = new Element("type");
		eType.setText(cs.getType());
		eCs.addContent(eType);
		
		Element eEnabled = new Element("enabled");
		eEnabled.setText(""+cs.isEnabled());
		eCs.addContent(eEnabled);
		
		Element eUrl = new Element("url");
		eUrl.setText(""+cs.getUrl());
		eCs.addContent(eUrl);
		
		eCs.addContent(XmlWorkspaceFactory.build(ws));
		
		return eCs;
	}
	
}
