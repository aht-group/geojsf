package org.geojsf.geoserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.geoserver.manager.GeoServerCoverageManager;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerLayerManager;
import org.geojsf.geoserver.manager.GeoServerStyleManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.geoserver.util.ConfigurationOverrider;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Coverage;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Layer;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerConfigurator.class);
	
	private Configuration config;
	private ConfigurationOverrider configOverrider;
	
	private Workspace workspace;
	
	private GeoServerWorkspaceManager workspaceManager;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerCoverageManager coverageManager;
	private GeoServerStyleManager styleManager;
	private GeoServerLayerManager layerManager;
	
	private File fBase;
	
	public GeoServerConfigurator(String configBaseDir, GeoServerRest rest, Configuration config) throws MalformedURLException
	{
		this.config=config;
		configOverrider = new ConfigurationOverrider(config);
		fBase = new File(configBaseDir);
		logger.info("Using configuration directory: "+fBase.getAbsolutePath());
		
		workspaceManager = new GeoServerWorkspaceManager(rest);
		dataStoreManager = new GeoServerDataStoreManager(rest);
		coverageManager = new GeoServerCoverageManager(rest);
		styleManager = new GeoServerStyleManager(rest);
		layerManager =  new GeoServerLayerManager(rest);
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
		configureStyles();
		configureDataStore();
		configureCoverageStore();
		configureLayer();
	}
	
	private void configureStyles() throws IOException
	{
		File f = new File(fBase,GeoServerLayerManager.xml);
		Layers layers = JaxbUtil.loadJAXB(f.getAbsolutePath(), Layers.class);
		for(Layer layer : layers.getLayer())
		{
			if(!styleManager.isAvailable(workspace, layer.getStyle().getName()))
			{
				configureStyle(layer.getStyle());
			}
		}
	}
	
	private void configureLayer() throws FileNotFoundException
	{
		File f = new File(fBase,GeoServerLayerManager.xml);
		Layers layers = JaxbUtil.loadJAXB(f.getAbsolutePath(), Layers.class);
		for(Layer layer : layers.getLayer())
		{
			layerManager.updateLayer(workspace,layer);
		}
	}
	
	private void configureStyle(Style style) throws IOException
	{
		logger.info("Configuring style "+style.getName());
		File f = new File(fBase,"styles"+File.separator+style.getName()+".xml");
		Document doc = JDomUtil.load(f);
		styleManager.createStyle(workspace.getName(),doc);
	}
	
	private void configureDataStore() throws GeoServerConfigurationException
	{
		logger.info("Configuring datasources");
		try
		{
			DataStores dataStores = JaxbUtil.loadJAXB(new File(fBase,GeoServerDataStoreManager.dsXml), DataStores.class);
			configOverrider.overrideDataStores(dataStores);
			
			JaxbUtil.trace(dataStores);
			for(DataStore ds : dataStores.getDataStore())
			{
				JaxbUtil.trace(ds);
				if(dataStoreManager.isAvailable(workspace,ds))
				{
					logger.info(DataStore.class.getSimpleName()+" "+ds.getName()+" is available and will not be re-created");
				}
				else
				{
					logger.info(DataStore.class.getSimpleName()+" "+ds.getName()+" will be created");
					dataStoreManager.createDataStore(workspace, ds);
				}
			}
		}
		catch (FileNotFoundException e) {throw new GeoServerConfigurationException("File "+GeoServerDataStoreManager.dsXml+" not found");}
		catch (IOException e) {e.printStackTrace();}
	}
	
	private void configureCoverageStore() throws GeoServerConfigurationException
	{
		logger.info("Configuring "+CoverageStores.class.getSimpleName());
		try
		{
			CoverageStores coverageStores = JaxbUtil.loadJAXB(new File(fBase,GeoServerCoverageManager.xml), CoverageStores.class);
			configOverrider.overrideCoverageStores(coverageStores);
			
			JaxbUtil.trace(coverageStores);
			for(CoverageStore cs : coverageStores.getCoverageStore())
			{
				JaxbUtil.trace(cs);
				if(coverageManager.isAvailable(workspace,cs))
				{
					logger.info(CoverageStore.class.getSimpleName()+" "+cs.getName()+" is available and will not be re-created");
				}
				else
				{
					logger.info(CoverageStore.class.getSimpleName()+" "+cs.getName()+" will be created");
					coverageManager.createCoverageStore(workspace, cs);
					for(Coverage c : cs.getCoverages().getCoverage())
					{
						File f = new File(fBase,"coverages"+File.separator+c.getName()+".xml");
						Document coverage = JDomUtil.load(f);
						coverageManager.createCoverage(workspace.getName(), cs.getName(), coverage);
//						logger.info("Creating coverage "+c.getName());
					}
					
				}
			}
		}
		catch (FileNotFoundException e) {throw new GeoServerConfigurationException("File "+GeoServerDataStoreManager.dsXml+" not found");}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void createLayer(Layers layers)
	{
		logger.info("layers");
		for(Layer layer : layers.getLayer())
		{
			logger.info("NYI");
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerRest rest = new GeoServerRestWrapper(config);
		GeoServerConfigurator geoserver = new GeoServerConfigurator("target",rest,config);
		geoserver.configureWorkspace();
//		geoserver.createDataStore();
	}
}