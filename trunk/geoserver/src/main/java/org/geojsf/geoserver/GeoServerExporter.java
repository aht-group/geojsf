package org.geojsf.geoserver;

import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerConfigKeys;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.manager.GeoServerDataStoreManager;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.xml.geoserver.DataStores;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerExporter
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerExporter.class);
	
	private GeoServerDataStoreManager dataStoreManager;
	private Workspace workspace;
	
	public GeoServerExporter(Configuration config) throws MalformedURLException
	{
		workspace = XmlWorkspaceFactory.build(config.getString(GeoServerConfigKeys.workspace));
		
		GeoServerRest rest = new GeoServerRestWrapper(config);
		dataStoreManager = new GeoServerDataStoreManager(rest);
	}
	
	public void exportDataStores() throws IOException
	{
		DataStores dataStores = dataStoreManager.getDataStores(workspace);
		JaxbUtil.info(dataStores);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = null; //Create your config here!	
		
		GeoServerExporter geoserver = new GeoServerExporter(config);
		geoserver.exportDataStores();
	}
}