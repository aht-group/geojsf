package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.geoserver.Workspaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerWorkspaceManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerWorkspaceManager.class);
	
	private GeoServerRest rest;
	public static String cjgKeyWorkspace = "geoserver.workspace";
	public static String wsXml = "workspace.xml";

	public GeoServerWorkspaceManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public Workspaces getWorkspaces() throws IOException {return rest.getWorkspaces();}
	public Workspace getWorkspace(String workspaceName) throws IOException {return rest.getWorkspace(workspaceName);}
	
	public boolean isAvailable(Workspace workspace) throws IOException
	{
		Set<String> set = new HashSet<String>();
		for(Workspace ws : getWorkspaces().getWorkspace())
		{
			set.add(ws.getName());
		}
		return set.contains(workspace.getName());
	}
	
	public void create(Workspace workspace) throws GeoServerConfigurationException,IOException
	{
		if(isAvailable(workspace))
		{
			throw new GeoServerConfigurationException("The workspace "+workspace.getName()+" already exists");
		}
		else
		{
			rest.createWorkspace(workspace);
			if(!isAvailable(workspace))
			{
				StringBuffer sb = new StringBuffer();
				sb.append("Tried to create workspace ").append(workspace.getName()).append(", but this failed!");
				logger.error(sb.toString());
				throw new GeoServerConfigurationException(sb.toString());
			}
		}
	}
}