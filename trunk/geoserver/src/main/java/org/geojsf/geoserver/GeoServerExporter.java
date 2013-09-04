package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerCoverageManager;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerLayerManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerExporter
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerExporter.class);
	
	private Workspace workspace;
	
	private GeoServerWorkspaceManager wsManager;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerCoverageManager coverageStoreManager;
	private GeoServerLayerManager layerManager;
	
	private File fBase;
	
	public GeoServerExporter(String configBaseDir, GeoServerRest rest) throws MalformedURLException
	{
		wsManager = new GeoServerWorkspaceManager(rest);
		dataStoreManager = new GeoServerDataStoreManager(rest);
		coverageStoreManager = new GeoServerCoverageManager(rest);
		layerManager = new GeoServerLayerManager(rest);
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
			logger.info("Starting export of workspace "+workspace.getName());
			workspace = wsManager.getWorkspace(workspaceName);
			JaxbUtil.save(new File(fBase,GeoServerWorkspaceManager.wsXml), workspace, true);
			exportDataStores();
			exportCoverageStores();
			exportLayers();
		}
	}
	
	public void exportDataStores() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" datastores");
		DataStores dataStores = dataStoreManager.getDataStores(workspace);
		JaxbUtil.trace(dataStores);
		JaxbUtil.save(new File(fBase,GeoServerDataStoreManager.dsXml), dataStores, true);
	}
	
	public void exportCoverageStores() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" coveragestores");
		CoverageStores cs = coverageStoreManager.getCoverageStores(workspace);
		JaxbUtil.trace(cs);
		JaxbUtil.save(new File(fBase,GeoServerCoverageManager.xml), cs, true);
	}
	
	public void exportLayers() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" layers");
		Layers layers = layerManager.getLayer(workspace);
		JaxbUtil.info(layers);
		JaxbUtil.save(new File(fBase,GeoServerLayerManager.xml), layers, true);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerExporter geoserver = new GeoServerExporter("target",new GeoServerRestWrapper(config));
		geoserver.exportWorkspace("myWsName");
		geoserver.exportDataStores();
	}
}