package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.xml.identity.User;
import net.sf.exlp.xml.net.Host;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.Postgis;
import org.geojsf.xml.geoserver.ShapeDir;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLayerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static void transform(Element eLayer)
	{
		logger.trace("Transforming ... ");
		SimpleXmlTranscoder.name(eLayer);
		SimpleXmlTranscoder.description(eLayer);
		SimpleXmlTranscoder.elementToAttribute(eLayer, "type");
		SimpleXmlTranscoder.elementToAttribute(eLayer, "enabled");
		
		Element eResource = eLayer.getChild("resource", SimpleXmlTranscoder.ns);
		Element eName = eResource.getChild("name",SimpleXmlTranscoder.ns);
		String resourceClass = eResource.getAttribute("class").getValue();
		if(resourceClass.equals("coverage"))
		{
			Element eCoverage = new Element(XmlCoverageStoreFactory.eName,SimpleXmlTranscoder.ns);
			eCoverage.setAttribute("name", eName.getValue());
			eLayer.addContent(eCoverage);
		}
		else if(resourceClass.equals("featureType"))
		{
			logger.warn("No handling for featureType");
		}
		else
		{
			logger.warn("Unknwon class: "+resourceClass);
		}
	}	
}