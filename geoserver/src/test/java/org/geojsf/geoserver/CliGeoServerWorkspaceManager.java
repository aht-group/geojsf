package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.model.xml.geoserver.Workspaces;
import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CliGeoServerWorkspaceManager
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerWorkspaceManager.class);
	
	private GeoServerWorkspaceManager workspaceManager;
	
	public CliGeoServerWorkspaceManager(Configuration config, GeoServerRest rest)
	{
		workspaceManager = init(rest);
	}

	public GeoServerWorkspaceManager init(GeoServerRest rest)
	{
//jeesl.highlight:init
		GeoServerWorkspaceManager workspaceManager = new GeoServerWorkspaceManager(rest);
//jeesl.highlight:init
		return workspaceManager;
	}
	
	public void basic(GeoServerRest rest) throws IOException
	{
		String wsName = "ws";
		Workspace ws = XmlWorkspaceFactory.build(wsName);
//jeesl.highlight:basic
		boolean available = workspaceManager.isAvailable(ws);
		Workspaces workspaces = workspaceManager.getWorkspaces();
//jeesl.highlight:basic
	}

	public void manager() throws IOException, GeoServerConfigurationException
	{
//jeesl.highlight:create
		Workspace ws = new Workspace();
		ws.setName("myWorkspace");
		workspaceManager.create(ws);
//jeesl.highlight:create
	}
	
	public void documentation() throws IOException
	{
		Workspaces workspaces = workspaceManager.getWorkspaces();
		JaxbUtil.save(new File("../doc/src/main/resources/code.geojsf/geoserver/jaxb/workspaces.xml"), workspaces, true);
		JaxbUtil.debug(workspaces);
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		
		CliGeoServerWorkspaceManager test = new CliGeoServerWorkspaceManager(config,rest);
		test.documentation();
//		test.basic(rest);
//		test.manager();
	}
}