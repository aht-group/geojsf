package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerWorkspaceManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerWorkspaceManager.class);
	
	private GeoServerRest rest;

	public GeoServerWorkspaceManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public Workspaces workspaces() throws IOException {return rest.workspaces();}
	
	public void createWorkspace(Workspace workspace) throws IOException
	{
		Set<String> set = new HashSet<String>();
		for(Workspace ws : workspaces().getWorkspace())
		{
			set.add(ws.getName());
		}
		if(set.contains(workspace.getName()))
		{
			logger.warn("Workspace already exists");
		}
		else
		{
			rest.createWorkspace(workspace);
		}
	}
}