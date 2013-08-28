package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerExporter
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerExporter.class);
	
	private Workspace workspace;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerWorkspaceManager wsManager;
	private File fBase;
	
	public GeoServerExporter(String configBaseDir, GeoServerRest rest) throws MalformedURLException
	{
		wsManager = new GeoServerWorkspaceManager(rest);
		dataStoreManager = new GeoServerDataStoreManager(rest);
		fBase = new File(configBaseDir);
		logger.info("Writing to configuration directory: "+fBase.getAbsolutePath());
	}
	
	public void exportWorkspace(String workspaceName) throws IOException
	{
		this.workspace = XmlWorkspaceFactory.build(workspaceName);
		if(!wsManager.isAvailable(workspace))
		{
			logger.warn("The workspace "+workspace.getName()+" is not available!");
		}
		else
		{
			logger.info("Starting export of worksapce "+workspace.getName());
			workspace = wsManager.getWorkspace(workspaceName);
			JaxbUtil.save(new File(fBase,"workspace.xml"), workspace, true);
			JaxbUtil.info(workspace);
		}
	}
	
	public void exportDataStores() throws IOException
	{
		DataStores dataStores = dataStoreManager.getDataStores(workspace);
		JaxbUtil.info(dataStores);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerExporter geoserver = new GeoServerExporter("target",new GeoServerRestWrapper(config));
		geoserver.exportWorkspace("myWsName");
		geoserver.exportDataStores();
	}
}