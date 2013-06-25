package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
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
//ahtutils.highlight:init
		GeoServerWorkspaceManager workspaceManager = new GeoServerWorkspaceManager(rest);
//ahtutils.highlight:init
		return workspaceManager;
	}
	
	public void basic(GeoServerRest rest) throws IOException
	{
//ahtutils.highlight:basic		
		Workspaces workspaces = workspaceManager.getWorkspaces();
//ahtutils.highlight:basic
	}

	public void manager() throws IOException
	{
//ahtutils.highlight:create
		Workspace workspace = new Workspace();
		workspace.setName("myWorkspace");
		workspaceManager.createWorkspace(workspace);
//ahtutils.highlight:create
	}

	
	public void documentation() throws IOException
	{
		Workspaces workspaces = workspaceManager.getWorkspaces();
		JaxbUtil.save(new File("../doc/src/main/resources/code.geojsf/geoserver/jaxb/workspaces.xml"), workspaces, true);
		JaxbUtil.debug(workspaces);
	}
	
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		
		CliGeoServerWorkspaceManager test = new CliGeoServerWorkspaceManager(config,rest);
		test.documentation();
//		test.basic(rest);
//		test.manager();
	}
}