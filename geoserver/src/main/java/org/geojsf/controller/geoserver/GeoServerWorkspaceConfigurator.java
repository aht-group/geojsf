package org.geojsf.controller.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList.RESTShortWorkspace;

import java.io.Serializable;

import org.geojsf.exception.GeoServerConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerWorkspaceConfigurator implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerWorkspaceConfigurator.class);
	public static final long serialVersionUID=1;
	
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	public GeoServerWorkspaceConfigurator(GeoServerRESTReader reader, GeoServerRESTPublisher publisher)
	{
		this.reader=reader;
		this.publisher=publisher;
	}
	
	public boolean createWorkspace(String workspace) throws GeoServerConfigurationException
	{
		boolean exists = false;
		RESTWorkspaceList list = reader.getWorkspaces();
		for(int i=0;i<list.size();i++)
		{
			RESTShortWorkspace w = list.get(i);
			logger.trace(w.getName());
			if(w.getName().equals(workspace)){exists=true;}
		}
		if(!exists){exists=buildWorkspace(workspace);}
		return exists;
	}
	
	private boolean buildWorkspace(String workspace)
	{
		boolean created = publisher.createWorkspace(workspace);
		logger.info("Created Workspace "+created);
		return created;
	}
}