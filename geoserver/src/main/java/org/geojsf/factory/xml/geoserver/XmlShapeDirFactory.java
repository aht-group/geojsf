package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.exlp.XmlDatabaseFactory;
import org.geojsf.factory.xml.exlp.XmlHostFactory;
import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlShapeDirFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlShapeDirFactory.class);
	public static final long serialVersionUID=1;
	
	public static String keyUrl = "url";
	public static String keyCharSet = "charset";
	public static String keyMemoryBuffer = "memory mapped buffer";
	public static String keyMemoryMaps = "cache and reuse memory maps";
	public static String keyCreateSpatialIndex = "create spatial index";
	public static String keyEnableSpatialIndex = "enable spatial index";
	public static String keyTimezone = "timezone";
	
	public static String keyFstype = "fstype";
	public static String keyFiletype = "filetype";
	
	public static void transform(Element dataStore)
	{
		logger.info("Transforming ... ");
		Element eShapeDir = new Element("shapeDir",SimpleXmlTranscoder.ns);
		Element eSpatial = new Element("spatial",SimpleXmlTranscoder.ns);
		
		Element connectionParameters = dataStore.getChild("connectionParameters", SimpleXmlTranscoder.ns);
		connectionParameters.detach();
		eShapeDir.addContent(connectionParameters);
		
		connectionParameters.setName("connection");
		
		List<Element> list = connectionParameters.getChildren("entry",SimpleXmlTranscoder.ns);
		List<Element> listDetach = new ArrayList<Element>();
		for(Element e : list)
		{
			if(e.getAttribute("key") != null)
			{
				if(e.getAttributeValue("key").equals(keyUrl)){eShapeDir.setAttribute(keyUrl, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyCharSet)){eShapeDir.setAttribute(keyCharSet, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyMemoryBuffer)){eShapeDir.setAttribute("memoryBuffer", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyMemoryMaps)){eShapeDir.setAttribute("cacheMemoryMaps", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyTimezone)){eShapeDir.setAttribute(keyTimezone, e.getValue());listDetach.add(e);}
				
				if(e.getAttributeValue("key").equals(keyFstype)){eShapeDir.setAttribute(keyFstype, e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyFiletype)){eShapeDir.setAttribute(keyFiletype, e.getValue());listDetach.add(e);}
				
				if(e.getAttributeValue("key").equals(keyCreateSpatialIndex)){eSpatial.setAttribute("createIndex", e.getValue());listDetach.add(e);}
				if(e.getAttributeValue("key").equals(keyEnableSpatialIndex)){eSpatial.setAttribute("enableIndex", e.getValue());listDetach.add(e);}
			}
		}
		for(Element e : listDetach){e.detach();}
		
		eShapeDir.addContent(eSpatial);
		dataStore.addContent(eShapeDir);
		
	}
}
