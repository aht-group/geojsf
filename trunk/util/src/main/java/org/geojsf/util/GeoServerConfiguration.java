package org.geojsf.util;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.GeoServerConfig;
import org.geojsf.factory.geoserver.GeoServerDataStoreFactory;
import org.geojsf.factory.geoserver.GeoServerRestFactory;
import org.geojsf.factory.geoserver.GeoServerWorkspaceFactory;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.xml.geoserver.DataStore;
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
	
	private String workspace,ds;
	
	public GeoServerConfiguration(Configuration config) throws MalformedURLException
	{
		this.config=config;
		reader = GeoServerRestFactory.reader(config);
		publisher = GeoServerRestFactory.publisher(config);	
		workspace = config.getString(GeoServerConfig.workspace);
		ds = config.getString(GeoServerConfig.dsName);
		
	}
	
	public void createWorkspace()
	{
		
		GeoServerWorkspaceFactory f = new GeoServerWorkspaceFactory(reader,publisher);
		boolean wsAvailable = f.createWorkspace(workspace);
		logger.info("Workspace "+workspace+" available: "+wsAvailable);
	}
	
	public void createDataStore()
	{
		DataStore ds = XmlDataStoreFactory.build(config);
		JaxbUtil.info(ds);
		
		GeoServerDataStoreFactory f = new GeoServerDataStoreFactory(reader,publisher);
		f.createDataStore(config.getString(GeoServerConfig.workspace),ds);
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

		publisher.publishExternalGeoTIFF(workspace, "c", new File("test"), "a", "b");
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerConfiguration geoserver = new GeoServerConfiguration(config);
		geoserver.createWorkspace();
		geoserver.createDataStore();
	}
}