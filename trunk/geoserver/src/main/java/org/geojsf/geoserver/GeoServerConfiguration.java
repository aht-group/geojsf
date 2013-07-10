package org.geojsf.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.factory.geoserver.GeoServerDataStoreFactory;
import org.geojsf.factory.geoserver.GeoServerRestFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.util.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Layers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerConfiguration
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerConfiguration.class);
	
	private Configuration config;
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	private GeoServerWorkspaceManager workspaceManager;
	private Workspace workspace;
	private String ds;
	
	public GeoServerConfiguration(Configuration config) throws MalformedURLException
	{
		this.config=config;
		reader = GeoServerRestFactory.reader(config);
		publisher = GeoServerRestFactory.publisher(config);
		workspace = XmlWorkspaceFactory.build(config.getString(GeoServerConfigKeys.workspace));
		ds = config.getString(GeoServerConfigKeys.dsName);
		
		GeoServerRest rest = new GeoServerRestWrapper(config);
		workspaceManager = new GeoServerWorkspaceManager(rest);
	}
	
	public void configureWorkspace() throws GeoServerConfigurationException, IOException
	{
		logger.info("Configuring workspace "+workspace.getName());
		boolean wsAvailable = workspaceManager.isAvailable(workspace);
		if(wsAvailable)
		{
			logger.info("The workspace "+workspace.getName()+" is already available. Not requird to re-configure.");
		}
		else
		{
			workspaceManager.create(workspace);
			logger.info("The workspace "+workspace.getName()+" was created.");
		}
	}
	
	public void createDataStore()
	{
		DataStore ds = XmlDataStoreFactory.build(config);
		JaxbUtil.info(ds);
		
		GeoServerDataStoreFactory f = new GeoServerDataStoreFactory(reader,publisher);
		f.createDataStore(config.getString(GeoServerConfigKeys.workspace),ds);
	}
	
	public void createLayer(Layers layers)
	{
		logger.info("layers");
		for(Layer layer : layers.getLayer())
		{
			logger.info("NYI");
		}
	}
	
	public void tiff() throws FileNotFoundException
	{
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		fte.setName("test");
		
		GSLayerEncoder layerEncoder = new GSLayerEncoder();
		layerEncoder.setEnabled(true);

		publisher.publishExternalGeoTIFF(workspace.getName(), "c", new File("test"), "a", "b");
	}
	
	public void layers()
	{
		RESTStyleList list = reader.getStyles();
		
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerConfiguration geoserver = new GeoServerConfiguration(config);
		geoserver.configureWorkspace();
		geoserver.createDataStore();
	}
}