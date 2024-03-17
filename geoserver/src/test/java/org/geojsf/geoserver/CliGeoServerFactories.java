package org.geojsf.geoserver;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.factory.xml.geoserver.XmlDataStoreFactory;
import org.geojsf.factory.xml.geoserver.XmlWorkspaceFactory;
import org.geojsf.geoserver.rest.GeoServerRestWrapper;
import org.geojsf.model.xml.geoserver.DataStore;
import org.geojsf.model.xml.geoserver.Workspace;
import org.geojsf.test.GeoJsfBootstrap;
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
//jeesl.highlight:examples
		Workspace workspace = XmlWorkspaceFactory.build("myWorkspace");
		DataStore ds = XmlDataStoreFactory.build("myDataSource");
//jeesl.highlight:examples
		JaxbUtil.info(ds);
		JaxbUtil.info(workspace);
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = GeoJsfBootstrap.init();
			
		GeoServerRest rest = new GeoServerRestWrapper(config);
		
		CliGeoServerFactories test = new CliGeoServerFactories(config,rest);
		test.examples();
	}
}