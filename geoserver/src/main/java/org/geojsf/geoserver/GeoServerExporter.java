package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerCoverageManager;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.manager.GeoServerFeatureTypeManager;
import org.geojsf.geoserver.manager.GeoServerLayerManager;
import org.geojsf.geoserver.manager.GeoServerStyleManager;
import org.geojsf.geoserver.manager.GeoServerWorkspaceManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Coverage;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.CoverageStores;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.FeatureTypes;
import org.geojsf.xml.geoserver.Layer;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerExporter
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerExporter.class);
	
	private Workspace workspace;
	
	private GeoServerWorkspaceManager wsManager;
	private GeoServerDataStoreManager dataStoreManager;
	private GeoServerCoverageManager coverageStoreManager;
	private GeoServerFeatureTypeManager ftManager;
	private GeoServerLayerManager layerManager;
	private GeoServerStyleManager styleManager;
	
	private File fBase;
	
	public GeoServerExporter(String configBaseDir, GeoServerRest rest) throws MalformedURLException
	{
		wsManager = new GeoServerWorkspaceManager(rest);
		dataStoreManager = new GeoServerDataStoreManager(rest);
		coverageStoreManager = new GeoServerCoverageManager(rest);
		ftManager = new GeoServerFeatureTypeManager(rest);
		layerManager = new GeoServerLayerManager(rest);
		styleManager = new GeoServerStyleManager(rest);
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
			exportFeatureTypes();
			exportLayers();
			exportStyles();
		}
	}
	
	public void exportDataStores() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" datastores");
		DataStores dataStores = dataStoreManager.getDataStores(workspace);
		JaxbUtil.trace(dataStores);
		JaxbUtil.save(new File(fBase,GeoServerDataStoreManager.dsXml), dataStores, true);
	}
	
	public void exportFeatureTypes() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" coveragestores");
		FeatureTypes fTypes = ftManager.getFeatureTypes(workspace);
		JaxbUtil.trace(fTypes);
		JaxbUtil.save(new File(fBase,GeoServerFeatureTypeManager.xml), fTypes, true);
		for(FeatureType ft : fTypes.getFeatureType())
		{
			Document doc = ftManager.exportFeatureType(workspace.getName(), ft.getDataStore().getName(), ft.getName());
			File f = new File(fBase,"featureTypes"+File.separator+ft.getName()+".xml");
			JDomUtil.save(doc, f, Format.getRawFormat());
		}
		
	}
	
	public void exportCoverageStores() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" coveragestores");
		CoverageStores cStores = coverageStoreManager.getCoverageStores(workspace);
		JaxbUtil.trace(cStores);
		JaxbUtil.save(new File(fBase,GeoServerCoverageManager.xml), cStores, true);
		
		for(CoverageStore cs : cStores.getCoverageStore())
		{
			for(Coverage c : cs.getCoverages().getCoverage())
			{
				Document doc = coverageStoreManager.getCoverage(workspace.getName(), cs.getName(), c.getName());
				File f = new File(fBase,"coverages"+File.separator+c.getName()+".xml");
				JDomUtil.save(doc, f, Format.getRawFormat());
			}
		}
	}
	
	public void exportLayers() throws IOException
	{
		logger.info("Starting export of "+workspace.getName()+" layers");
		Layers layers = layerManager.getLayer(workspace);
		JaxbUtil.trace(layers);
		JaxbUtil.save(new File(fBase,GeoServerLayerManager.xml), layers, true);
	}
	
	public void exportStyles() throws IOException
	{
		File f = new File(fBase,GeoServerLayerManager.xml);
		Layers layers = JaxbUtil.loadJAXB(f.getAbsoluteFile(), Layers.class);
		for(Layer layer : layers.getLayer())
		{
			logger.trace(layer.getName());
			exportStyle(layer.getStyle());
		}
	}
	
	private void exportStyle(Style style) throws IOException
	{
		logger.info("Loading style: "+style.getName());
		Document doc = styleManager.getStyle(workspace.getName(),style.getName());
		File f = new File(fBase,"styles"+File.separator+style.getName()+".xml");
		JDomUtil.save(doc, f, Format.getPrettyFormat());
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerExporter geoserver = new GeoServerExporter("target",new GeoServerRestWrapper(config));
		geoserver.exportWorkspace("myWsName");
		geoserver.exportDataStores();
	}
}