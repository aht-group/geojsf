package org.geojsf.geoserver;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.test.GeoJsfGeoServerTestBootstrap;
import org.geojsf.util.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.xml.geoserver.DataStore;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliGeoServerFactories
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerFactories.class);

	public CliGeoServerFactories(Configuration config, GeoServerRest rest)
	{

	}

	public void examples()
	{
//ahtutils.highlight:examples
		Workspace workspace = XmlWorkspaceFactory.build("myWorkspace");
		DataStore ds = XmlDataStoreFactory.build("myDataSource");
//ahtutils.highlight:examples
		JaxbUtil.info(ds);
		JaxbUtil.info(workspace);
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfGeoServerTestBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		
		CliGeoServerFactories test = new CliGeoServerFactories(config,rest);
		test.examples();
	}
}