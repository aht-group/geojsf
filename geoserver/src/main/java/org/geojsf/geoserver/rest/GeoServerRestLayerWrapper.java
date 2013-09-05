package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlLayerFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.interfaces.rest.geoserver.GeoServerLayerRest;
import org.geojsf.xml.geoserver.Layer;
import org.geojsf.xml.geoserver.Layers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestLayerWrapper implements GeoServerLayerRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestLayerWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestLayerWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	@Override
	public Layers allLayers() throws IOException
	{
		logger.trace("allLayers()");
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.getLayers(), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		
//		JDomUtil.debug(root);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Element e : root.getChildren("layer",GeoServerRestWrapper.ns))
		{
			GeoServerXmlTranscoder.layer(e);
		}
//		JDomUtil.debug(root);
		
		Layers result = JDomUtil.toJaxb(root, Layers.class);
		return result;
	}

	@Override
	public Layer getLayer(String layer) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.layer(layer), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlLayerFactory.transform(root);
//		JDomUtil.debug(root);
		
		return JDomUtil.toJaxb(root, Layer.class);
	}
	
}