package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlCoverageFactory;
import org.geojsf.factory.xml.geoserver.XmlCoverageStoreFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.interfaces.rest.geoserver.GeoServerCoverageRest;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.Coverages;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestCoverageWrapper implements GeoServerCoverageRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestCoverageWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestCoverageWrapper(GeoServerRestInterface rest)
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
		return JDomUtil.toJaxb(root, CoverageStores.class);
	}

	@Override
	public CoverageStore coverageStore(String workSpace, String coverageStore) throws IOException
	{
		logger.trace("Requesting: "+workSpace+" "+coverageStore);
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.coverageStore(workSpace,coverageStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlCoverageStoreFactory.transform(root);
//		JDomUtil.debug(root);
		
		CoverageStore cs = JDomUtil.toJaxb(root, CoverageStore.class);		
		return cs;
	}
	
	@Override
	public void createCoverageStore(Workspace ws,CoverageStore cs)
	{
		Document doc = new Document();
		doc.setRootElement(XmlCoverageStoreFactory.build(ws,cs));

//		JDomUtil.debug(doc);

		rest.createCoverageStore(ws.getName(),JDomUtil.toString(doc));
	}

	@Override
	public Coverages getCoverages(String workSpace, String coverageStore) throws IOException
	{
		logger.trace("Requesting: "+workSpace+" "+coverageStore);
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.coverages(workSpace,coverageStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Element e : root.getChildren("coverage",GeoServerRestWrapper.ns))
		{
			XmlCoverageFactory.transform(e);
		}
		
//		JDomUtil.debug(root);
		
		return JDomUtil.toJaxb(root, Coverages.class);
	}

	@Override
	public Document getCoverage(String workSpace, String coverageStore, String coverage) throws IOException
	{
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.coverage(workSpace,coverageStore,coverage), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		return doc;
	}

	@Override
	public void createCoverage(String workSpace, String coverageStore,Document coverage) throws IOException
	{
		logger.trace("createCoverage "+workSpace+" - "+" "+coverageStore);
//		JDomUtil.debug(doc);
		rest.createCoverage(workSpace,coverageStore,JDomUtil.toString(coverage));
		
	}
}