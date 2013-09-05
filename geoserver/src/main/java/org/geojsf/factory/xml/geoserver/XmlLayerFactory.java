package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
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
			Element eFeatureType = new Element(XmlFeatureTypeFactory.eName,SimpleXmlTranscoder.ns);
			eFeatureType.setAttribute("name", eName.getValue());
			eLayer.addContent(eFeatureType);	
		}
		else
		{
			logger.warn("Unknwon class: "+resourceClass);
		}
		
		logger.info("Transforming style" );
		Element eStyle = eLayer.getChild("defaultStyle", SimpleXmlTranscoder.ns);
		if(eStyle!=null){XmlStyleFactory.transform(eStyle);}
		
		Element eStyles = eLayer.getChild("styles", SimpleXmlTranscoder.ns);
		if(eStyles!=null){XmlStylesFactory.transform(eStyles);}
	}	
}