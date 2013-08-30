package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspacesFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestDataStoreWrapper
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestDataStoreWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestDataStoreWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	public DataStores getDataStores(String workspace) throws IOException
	{		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStores(workspace), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		for(Object o : root.getChildren("dataStore",GeoServerRestWrapper.ns))
		{
			if(o instanceof Element)
			{
				Element e = (Element)o;
				GeoServerXmlTranscoder.dataStore(e);
			}
		}
		
		DataStores result = new DataStores();
		
		DataStores first = JDomUtil.toJaxb(root, DataStores.class);
		for(DataStore ds : first.getDataStore())
		{
			result.getDataStore().add(dataStore(workspace, ds.getName()));
		}
		
		return result;
	}
	
	public DataStore dataStore(String workspace, String dataStore) throws IOException
	{		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStore(workspace,dataStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
	 
		XmlDataStoreFactory.transform(root);
//		JDomUtil.debug(doc);
		
		return JDomUtil.toJaxb(root, DataStore.class);
	}
	
	public void createDataStore(DataStore ds, Workspace ws)
	{
		Document doc = new Document();
		doc.setRootElement(XmlDataStoreFactory.build(ds, ws));

		JDomUtil.debug(doc);
		
		rest.createDatastore(ws.getName(),JDomUtil.toString(doc));
	}
}