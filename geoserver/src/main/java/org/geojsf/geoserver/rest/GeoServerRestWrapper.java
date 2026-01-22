package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.exlp.interfaces.system.property.Configuration;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.api.rest.geoserver.GeoServerRestInterface;
import org.geojsf.model.xml.geoserver.CoverageStore;
import org.geojsf.model.xml.geoserver.CoverageStores;
import org.geojsf.model.xml.geoserver.Coverages;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.DataStores;
import org.geojsf.model.xml.geoserver.FeatureType;
import org.geojsf.model.xml.geoserver.FeatureTypes;
import org.geojsf.model.xml.geoserver.Layer;
import org.geojsf.model.xml.geoserver.Layers;
import org.geojsf.model.xml.geoserver.Styles;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.model.xml.geoserver.Workspaces;
import org.geojsf.util.GeoServerConfigKeys;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jeesl.util.web.RestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

public class GeoServerRestWrapper implements GeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";
	
	public static Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");

	private GeoServerRestCoverageWrapper csWrapper;
	private GeoServerRestLayerWrapper layerWrapper;
	private GeoServerRestFeatureTypeWrapper ftWrapper;
	private GeoServerRestStyleWrapper styleWrapper;
	
	public GeoServerRestWrapper(Configuration config)
	{
		this(config.getString(GeoServerConfigKeys.restUrl),
								config.getString(GeoServerConfigKeys.restUser),
								config.getString(GeoServerConfigKeys.restPassword));
	}
	
	public GeoServerRestWrapper(String url, String user, String password)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication(user, password));
		client.register(new RestLogger());
		ResteasyWebTarget target = client.target(url);
		rest = target.proxy(GeoServerRestInterface.class);
		
//		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
//		DefaultHttpClient client = new DefaultHttpClient();
//	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
//	    AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT, AuthScope.ANY_REALM);
//	    client.getCredentialsProvider().setCredentials(authscope, credentials);
//	    ApacheHttpClient4Executor executer = new ApacheHttpClient4Executor(client);
//		rest = ProxyFactory.create(GeoServerRestInterface.class, url, executer);
		
		logger.info("REST proxy created with URL="+url);
		styleWrapper = new GeoServerRestStyleWrapper(rest);
		layerWrapper=new GeoServerRestLayerWrapper(rest);
		ftWrapper=new GeoServerRestFeatureTypeWrapper(rest);
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


	// STYLE
	@Override public Document getStyle(String workspace, String style) throws IOException {return styleWrapper.getStyle(workspace,style);}
	@Override public void createStyle(String workspace, Document style)throws IOException {styleWrapper.createStyle(workspace,style);}
	@Override public Styles getStyles(String workspace) throws IOException {return styleWrapper.getStyles(workspace);}
	
	@Override
	public Workspaces getWorkspaces() throws IOException
	{
		GeoServerRestWorkspaceWrapper wsWrapper = new GeoServerRestWorkspaceWrapper(rest);
		return wsWrapper.getWorkspaces();
	}
	
	@Override
	public Workspace getWorkspace(String workspaceName) throws IOException
	{
		GeoServerRestWorkspaceWrapper wsWrapper = new GeoServerRestWorkspaceWrapper(rest);
		return wsWrapper.getWorkspace(workspaceName);
	}

	@Override
	public void createWorkspace(Workspace workspace) throws IOException
	{
		GeoServerRestWorkspaceWrapper wsWrapper = new GeoServerRestWorkspaceWrapper(rest);
		wsWrapper.createWorkspace(workspace);
	}

	// DATASTORE
	@Override
	public DataStores getDataStores(String workspace) throws IOException
	{
		GeoServerRestDataStoreWrapper dsWrapper = new GeoServerRestDataStoreWrapper(rest);
		return dsWrapper.getDataStores(workspace);
	}
	
	@Override
	public DataStore dataStore(String workspace, String dataStore) throws IOException
	{
		GeoServerRestDataStoreWrapper dsWrapper = new GeoServerRestDataStoreWrapper(rest);
		return dsWrapper.dataStore(workspace,dataStore);
	}

	@Override
	public void createDataStore(DataStore datastore, Workspace workspace) throws IOException
	{
		GeoServerRestDataStoreWrapper dsWrapper = new GeoServerRestDataStoreWrapper(rest);
		dsWrapper.createDataStore(datastore,workspace);
	}

	// COVERAGES
	@Override public CoverageStores getCoverageStores(String workspace) throws IOException
		{return getCsWrapper().getCoverageStores(workspace);}
	@Override public CoverageStore coverageStore(String workspace, String coverageStore) throws IOException
		{return getCsWrapper().coverageStore(workspace,coverageStore);}
	@Override public void createCoverageStore(Workspace ws,CoverageStore cs) throws IOException
		{getCsWrapper().createCoverageStore(ws,cs);}
	@Override public Coverages getCoverages(String workSpace, String coverageStore) throws IOException
		{return getCsWrapper().getCoverages(workSpace,coverageStore);}
	@Override public Document getCoverage(String workSpace, String coverageStore, String coverage) throws IOException
		{return getCsWrapper().getCoverage(workSpace,coverageStore,coverage);}
	@Override public void createCoverage(String workSpace, String coverageStore,Document coverage) throws IOException
		{getCsWrapper().createCoverage(workSpace,coverageStore,coverage);}
	
	// LAYER
	@Override public Layers allLayers() throws IOException {return layerWrapper.allLayers();}
	@Override public Layer getLayer(String layer) throws IOException {return layerWrapper.getLayer(layer);}
	
	// FEATURE TYPES
	@Override public FeatureTypes getFeatureTypes(String ws, String ds) throws IOException
		{return ftWrapper.getFeatureTypes(ws, ds);}
	@Override public FeatureType getFeatureType(String ws, String ds, String ft) throws IOException
		{return ftWrapper.getFeatureType(ws, ds, ft);}
	@Override public Document exportFeatureType(String workSpace, String coverageStore,String featureType) throws IOException
		{return ftWrapper.exportFeatureType(workSpace, coverageStore, featureType);}
	@Override public void createFeatureType(String workSpace, String dataStore,Document featureType) throws IOException
		{ftWrapper.createFeatureType(workSpace, dataStore, featureType);}
	
	private GeoServerRestCoverageWrapper getCsWrapper()
	{
		if(csWrapper==null){csWrapper=new GeoServerRestCoverageWrapper(rest);}
		return csWrapper;
	}


	@Override public void updateLayer(Layer layer){layerWrapper.updateLayer(layer);}	
}