package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.api.rest.geoserver.GeoServerDataStoreRest;
import org.geojsf.api.rest.geoserver.GeoServerRestInterface;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.geojsf.model.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestDataStoreWrapper implements GeoServerDataStoreRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestDataStoreWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestDataStoreWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	@Override
	public DataStores getDataStores(String workspace) throws IOException
	{		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStores(workspace), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Element e : root.getChildren("dataStore",GeoServerRestWrapper.ns))
		{
			GeoServerXmlTranscoder.dataStore(e);
		}
		
		DataStores result = new DataStores();
		
		DataStores first = JDomUtil.toJaxb(root, DataStores.class);
		for(DataStore ds : first.getDataStore())
		{
			result.getDataStore().add(dataStore(workspace, ds.getName()));
		}
		
		return result;
	}
	
	@Override
	public DataStore dataStore(String workspace, String dataStore) throws IOException
	{		
		logger.info("REST.dataStore ws="+workspace+" ds="+dataStore);
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStore(workspace,dataStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		
		Element root = doc.getRootElement();
//		JDomUtil.debug(doc);
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlDataStoreFactory.transform(root);
//		JDomUtil.debug(root);
		
		return JDomUtil.toJaxb(root, DataStore.class);
	}
	
	@Override
	public void createDataStore(DataStore ds, Workspace ws)
	{
		Document doc = new Document();
		doc.setRootElement(XmlDataStoreFactory.build(ds, ws));

//		JDomUtil.debug(doc);
		
		rest.createDatastore(ws.getName(),JDomUtil.toString(doc));
	}
}