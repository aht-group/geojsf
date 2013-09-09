package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlStylesFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.interfaces.rest.geoserver.GeoServerStyleRest;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Styles;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestStyleWrapper implements GeoServerStyleRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestStyleWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestStyleWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	@Override
	public Document getStyle(String workspace, String style) throws IOException
	{
		InputStream is = IOUtils.toInputStream(rest.style(workspace, style), xmlEncoding);
//		IOUtils.copy(is, System.out);
		Document doc = JDomUtil.load(is, xmlEncoding);
		return doc;
	}

	@Override
	public void createStyle(String workspace, Document style) throws IOException
	{
		rest.createStyle(workspace,JDomUtil.toString(style));
	}

	@Override
	public Styles getStyles(String workspace) throws IOException
	{
		InputStream is = IOUtils.toInputStream(rest.styles(workspace), xmlEncoding);
		
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
		XmlStylesFactory.transform(root);
		
		return JDomUtil.toJaxb(root, Styles.class);
	}
}