package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.controller.interfaces.rest.GeoServerRestInterface;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.util.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.util.factory.xml.geoserver.XmlWorkspacesFactory;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Styles;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestWrapper implements GeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestWrapper(Configuration config)
	{
		this(config.getString(GeoServerConfigKeys.restUrl),
								config.getString(GeoServerConfigKeys.restUser),
								config.getString(GeoServerConfigKeys.restPassword));
	}
	
	public GeoServerRestWrapper(String url, String user, String password)
	{
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		
		DefaultHttpClient client = new DefaultHttpClient();
	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
	    AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT, AuthScope.ANY_REALM);
	    client.getCredentialsProvider().setCredentials(authscope, credentials);
	    ApacheHttpClient4Executor executer = new ApacheHttpClient4Executor(client);
		
		rest = ProxyFactory.create(GeoServerRestInterface.class, url, executer);
		logger.info("REST proxy created with URL="+url);
	}

	@Override public Styles styles() throws IOException {return buildStyles(rest.styles());}
	@Override public Styles styles(String workspace) throws IOException {return buildStyles(rest.styles(workspace));}
	
	private Styles buildStyles(String xml) throws IOException
	{
		Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+xml, xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, ns);
		
		for(Object o : root.getChildren("style",ns))
		{
			if(o instanceof Element)
			{
				Element e = (Element)o;
				Element name = e.getChild("name",ns);
				e.setAttribute("name", name.getText());
			}
		}
	//	JDomUtil.debug(root);    
		
		return JDomUtil.toJaxb(root, Styles.class);
	}

	@Override
	public Document style(String name) throws IOException
	{
		InputStream is = IOUtils.toInputStream(rest.style(name), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		return doc;
	}
	
	@Override
	public Document style(String workspace, String name) throws IOException
	{
		InputStream is = IOUtils.toInputStream(rest.style(workspace,name), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		return doc;
	}

	@Override
	public void deleteStyle(String workspace, String name) throws IOException
	{
		rest.delete(workspace, name, true);
	}

	@Override
	public void deleteStyle(String name) throws IOException
	{
		rest.delete(name,true);
	}


	@Override
	public void updateStyle(String workspace, Document doc) throws IOException
	{
		rest.updateStyle(workspace,JDomUtil.toString(doc));
	}

	@Override
	public Workspaces getWorkspaces() throws IOException
	{
		Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.workspaces(), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, ns);
		XmlWorkspacesFactory.transform(root); 
		return JDomUtil.toJaxb(root, Workspaces.class);
	}

	@Override
	public void createWorkspace(Workspace workspace) throws IOException
	{
		Element root = new Element("workspace");
		Element name = new Element("name");
		name.setText(workspace.getName());
		root.addContent(name);
		
		Document doc = new Document();
		doc.setRootElement(root);

		rest.createWorkspace(JDomUtil.toString(doc));
		
	}

	@Override
	public DataStores getDataStores(String workspace) throws IOException
	{
		Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStores(workspace), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, ns);
	 
		for(Object o : root.getChildren("dataStore",ns))
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
	
	@Override
	public DataStore dataStore(String workspace, String dataStore) throws IOException
	{
		Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.dataStore(workspace,dataStore), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, ns);
	 
		XmlDataStoreFactory.transform(root);
//		JDomUtil.debug(doc);
		
		return JDomUtil.toJaxb(root, DataStore.class);
	}
}