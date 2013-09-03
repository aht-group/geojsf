package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlCoverageStoreFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.interfaces.rest.geoserver.GeoServerCoverageStoreRest;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestCoverageStoreWrapper implements GeoServerCoverageStoreRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestCoverageStoreWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestCoverageStoreWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	@Override
	public CoverageStores getCoverageStores(String workspace) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.coverageStores(workspace), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		
//		JDomUtil.debug(root);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Element e : root.getChildren("coverageStore",GeoServerRestWrapper.ns))
		{
			GeoServerXmlTranscoder.coverageStore(e);
		}
//		JDomUtil.debug(root);
		
		CoverageStores result = new CoverageStores();
		
		CoverageStores first = JDomUtil.toJaxb(root, CoverageStores.class);
		for(CoverageStore cs : first.getCoverageStore())
		{
			result.getCoverageStore().add(coverageStore(workspace, cs.getName()));
		}
		
		return result;
	}

	@Override
	public CoverageStore coverageStore(String workSpace, String coverageStore) throws IOException
	{
		logger.info("Requesting: "+workSpace+" "+coverageStore);
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.coverageStore(workSpace,coverageStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlCoverageStoreFactory.transform(root);
//		JDomUtil.debug(root);
		
		return JDomUtil.toJaxb(root, CoverageStore.class);
	}
}