package org.geojsf.geoserver.rest;

import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XmlUtil;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.interfaces.rest.GeoServerRestInterface;
import org.geojsf.util.factory.xml.geoserver.XmlWorkspacesFactory;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerRestWorkspaceWrapper
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerRestWorkspaceWrapper.class);
	
	private GeoServerRestInterface rest;
	private static final String xmlEncoding = "UTF-8";

	public GeoServerRestWorkspaceWrapper(GeoServerRestInterface rest)
	{
		this.rest=rest;
	}
	
	
	public Workspaces getWorkspaces() throws IOException
	{		
		InputStream is = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.workspaces(), xmlEncoding);
		Document doc = JDomUtil.load(is, xmlEncoding);
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
		XmlWorkspacesFactory.transform(root); 
		return JDomUtil.toJaxb(root, Workspaces.class);
	}
	
	public Workspace getWorkspace(String workspaceName) throws IOException
	{
		InputStream isNamespace = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.namespace(workspaceName), xmlEncoding);
		Document docNs = JDomUtil.load(isNamespace, xmlEncoding);
		//		JDomUtil.debug(docNs);
		
		InputStream isWorksapce = IOUtils.toInputStream(XmlUtil.defaultXmlHeader+rest.workspace(workspaceName), xmlEncoding);
		Document doc = JDomUtil.load(isWorksapce, xmlEncoding);
		
		Element root = doc.getRootElement();
		JDomUtil.setNameSpaceRecursive(root, GeoServerRestWrapper.ns);
		XmlWorkspaceFactory.transform(root);
		
		Workspace workspace = JDomUtil.toJaxb(root, Workspace.class);
		workspace.setNamespace(docNs.getRootElement().getChild("uri").getValue());
		
		return workspace;
	}

	public void createWorkspace(Workspace workspace) throws IOException
	{
		Element root = new Element("workspace");
		Element name = new Element("name");
		name.setText(workspace.getName());
		root.addContent(name);
		
		Document doc = new Document();
		doc.setRootElement(root);

		createNamespace(workspace.getName(), workspace.getNamespace());
//		rest.createWorkspace(JDomUtil.toString(doc));
	}
	
	public void createNamespace(String prefix, String uri) throws IOException
	{
		Element root = new Element("namespace");
		
		Element eUri = new Element("uri");
		eUri.setText(uri);
		root.addContent(eUri);
		
		Element ePrefix = new Element("prefix");
		ePrefix.setText(prefix);
		root.addContent(ePrefix);
		
		Document doc = new Document();
		doc.setRootElement(root);

		JDomUtil.debug(doc);
		
		rest.createNamespace(JDomUtil.toString(doc));
	}
}