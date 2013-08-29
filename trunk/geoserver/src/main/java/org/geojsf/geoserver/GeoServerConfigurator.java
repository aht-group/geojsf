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
import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.factory.geoserver.GeoServerDataStoreFactory;
import org.geojsf.factory.geoserver.GeoServerRestFactory;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.Workspace;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Layers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerConfigurator.class);
	
	private Configuration config;
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	private Workspace workspace;
	
	private GeoServerWorkspaceManager workspaceManager;
	private String ds;
	private File fBase;
	
	public GeoServerConfigurator(String configBaseDir, Configuration config) throws MalformedURLException
	{
		this.config=config;
		
		fBase = new File(configBaseDir);
		logger.info("Using configuration directory: "+fBase.getAbsolutePath());
		
		reader = GeoServerRestFactory.reader(config);
		publisher = GeoServerRestFactory.publisher(config);
		ds = config.getString(GeoServerConfigKeys.dsName);
		
		GeoServerRest rest = new GeoServerRestWrapper(config);
		workspaceManager = new GeoServerWorkspaceManager(rest);
	}
	
	public void configureWorkspace() throws GeoServerConfigurationException, IOException
	{
		workspace = JaxbUtil.loadJAXB(new File(fBase,GeoServerWorkspaceManager.wsXml), Workspace.class);
		logger.info("Configuring workspace "+workspace.getName());
		boolean wsAvailable = workspaceManager.isAvailable(workspace);
		if(wsAvailable)
		{
			logger.info("The workspace "+workspace.getName()+" is already available. Not requird to re-create.");
		}
		else
		{
			logger.info("The workspace "+workspace.getName()+" will now be created");
			workspaceManager.create(workspace);			
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
		
		GeoServerConfigurator geoserver = new GeoServerConfigurator("target",config);
		geoserver.configureWorkspace();
		geoserver.createDataStore();
	}
}