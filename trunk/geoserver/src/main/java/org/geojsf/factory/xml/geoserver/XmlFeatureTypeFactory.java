package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeFactory.class);
	public static final long serialVersionUID=1;
	
	public static String eName = "featureType";
	
	public static void transform(Element eFeatureType)
	{
		logger.trace("Transforming ... "+eName);
		SimpleXmlTranscoder.name(eFeatureType);
	
		Element eStore = eFeatureType.getChild("store", SimpleXmlTranscoder.ns);
		if(eStore!=null)
		{
			String storeClass = eStore.getAttribute("class").getValue();
			if(storeClass.equals("dataStore"))
			{
				eStore.setName("dataStore");
				XmlDataStoreFactory.transform(eStore);
			}
			else
			{
				logger.warn("Unknwon handling for storeClass="+storeClass);
			}
		}

	}
}