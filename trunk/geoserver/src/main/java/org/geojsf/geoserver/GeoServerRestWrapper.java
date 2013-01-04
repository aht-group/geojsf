package org.geojsf.geoserver;

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
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Styles;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestWrapper implements GeoServerRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestWrapper(Configuration config)
	{
		this(config.getString(GeoServerConfig.restHost),
								config.getString(GeoServerConfig.restUser),
								config.getString(GeoServerConfig.restPassword));
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
	}

	@Override
	public Styles styles() throws IOException
	{		
		Namespace ns = Namespace.getNamespace("g","http://www.geojsf.org/geoserver");
		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.styles(), xmlEncoding);
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
	public Styles styles(String prefixFilter) throws IOException
	{
		Styles styles = new Styles();
		for(Style style : this.styles().getStyle())
		{
			if(style.isSetName() && style.getName().startsWith(prefixFilter))
			{
				styles.getStyle().add(style);
			}
		}
		return styles;
	}

	@Override
	public Document style(String name) throws IOException
	{
		InputStream is = IOUtils.toInputStream(rest.style(name), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		return doc;
	}
}