package org.geojsf.geoserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.geojsf.exception.GeoServerConfigurationException;
import org.geojsf.geoserver.manager.GeoServerCoverageManager;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerFeatureTypeManager;
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
import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.FeatureTypes;
import org.geojsf.xml.geoserver.Layer;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class GeoServerConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerConfigurator.class);
	
	public static final String cfgKeyRestUrl = "geoserver.rest.import.url";
	public static final String cfgKeyRestUser = "geoserver.rest.import.user";
	public static final String cfgKeyRestPwd = "geoserver.rest.import.password";
	
	private ConfigurationOverrider configOverrider;
	
	private Workspace workspace;
	
	private GeoServerWorkspaceManager workspaceManager;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerCoverageManager coverageManager;
	private GeoServerFeatureTypeManager featureTypeManager;
	private GeoServerStyleManager styleManager;
	private GeoServerLayerManager layerManager;
	
	private String configBaseDir;
	
	public GeoServerConfigurator(String configBaseDir, GeoServerRest rest, Configuration config) throws MalformedURLException
	{
		this.configBaseDir=configBaseDir;
		logger.info("Using configuration directory: "+configBaseDir);
		
		configOverrider = new ConfigurationOverrider(config);

		workspaceManager = new GeoServerWorkspaceManager(rest);
		dataStoreManager = new GeoServerDataStoreManager(rest);
		coverageManager = new GeoServerCoverageManager(rest);
		styleManager = new GeoServerStyleManager(rest);
		layerManager =  new GeoServerLayerManager(rest);
		featureTypeManager = new GeoServerFeatureTypeManager(rest);
	}
	
	public void configureWorkspace() throws GeoServerConfigurationException, IOException
	{
		workspace = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerWorkspaceManager.wsXml, Workspace.class);
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
		configureFeatureTypes();
		configureCoverageStore();
		configureLayer();
	}
	
	private void configureStyles() throws IOException
	{
		Layers layers = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerLayerManager.xml, Layers.class);
		Set<String> set = new HashSet<String>();
		for(Layer layer : layers.getLayer())
		{
			if(!styleManager.isAvailable(workspace, layer.getStyle().getName()))
			{
				if(!set.contains(layer.getStyle().getName()))
				{
					logger.info("Does not contain ... "+layer.getStyle().getName());
					configureStyle(layer.getStyle());
					set.add(layer.getStyle().getName());
				}
				
			}
		}
	}
	
	private void configureLayer() throws FileNotFoundException
	{
		Layers layers = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerLayerManager.xml, Layers.class);
		for(Layer layer : layers.getLayer())
		{
			layerManager.updateLayer(workspace,layer);
		}
	}
	
	private void configureStyle(Style style) throws IOException
	{
		logger.info("Creating Style: "+style.getName());
		Document doc = JDomUtil.load(configBaseDir+File.separator+"styles"+File.separator+style.getName()+".sld");
//		if(style.getName().equals("cblt.water.quality.spot")){JDomUtil.debug(doc);}
		styleManager.createStyle(workspace.getName(),doc);
	}
	
	private void configureDataStore() throws GeoServerConfigurationException
	{
		logger.info("Configuring datasources");
		try
		{
			DataStores dataStores = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerDataStoreManager.dsXml, DataStores.class);
			
			JaxbUtil.trace(dataStores);
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
	
	private void configureFeatureTypes() throws GeoServerConfigurationException
	{
		try
		{
			FeatureTypes fts = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerFeatureTypeManager.xml, FeatureTypes.class);
			for(FeatureType ft : fts.getFeatureType())
			{
				Document doc = JDomUtil.load(configBaseDir+File.separator+"featureTypes"+File.separator+ft.getName()+".xml");
				featureTypeManager.createFeatureType(workspace.getName(), ft.getDataStore().getName(), doc);
			}
		}
		catch (FileNotFoundException e) {throw new GeoServerConfigurationException(e.getMessage());}
		catch (IOException e) {throw new GeoServerConfigurationException(e.getMessage());}
	}
	
	private void configureCoverageStore() throws GeoServerConfigurationException
	{
		logger.info("Configuring "+CoverageStores.class.getSimpleName());
		try
		{
			CoverageStores coverageStores = JaxbUtil.loadJAXB(configBaseDir+File.separator+GeoServerCoverageManager.xml, CoverageStores.class);
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
						Document coverage = JDomUtil.load(configBaseDir+File.separator+"coverages"+File.separator+c.getName()+".xml");
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