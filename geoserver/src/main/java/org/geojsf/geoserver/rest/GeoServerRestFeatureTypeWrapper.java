package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlFeatureTypeFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.interfaces.rest.geoserver.GeoServerFeatureTypeRest;
import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.FeatureTypes;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestFeatureTypeWrapper implements GeoServerFeatureTypeRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestFeatureTypeWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestFeatureTypeWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	@Override
	public FeatureTypes getFeatureTypes(String ws, String ds) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.featureTypes(ws, ds), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		
//		JDomUtil.debug(root);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Element e : root.getChildren("featureType",GeoServerRestWrapper.ns))
		{
			XmlFeatureTypeFactory.transform(e);
		}
//		JDomUtil.debug(root);
			
		return JDomUtil.toJaxb(root, FeatureTypes.class);
	}

	@Override
	public FeatureType getFeatureType(String ws, String ds, String ft) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.featureType(ws, ds, ft), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlFeatureTypeFactory.transform(root);
//		JDomUtil.debug(root);
		
		return JDomUtil.toJaxb(root, FeatureType.class);
	}

	@Override
	public Document exportFeatureType(String workSpace, String coverageStore, String coverage) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.featureType(workSpace,coverageStore,coverage), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
//		JDomUtil.debug(doc);
		return doc;
	}

	@Override
	public void createFeatureType(String workSpace, String dataStore, Document featureType) throws IOException
	{
		rest.createFeatureType(workSpace,dataStore,JDomUtil.toString(featureType));
	}
}