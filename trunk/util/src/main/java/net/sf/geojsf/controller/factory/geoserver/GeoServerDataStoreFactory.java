package net.sf.geojsf.controller.factory.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.encoder.GSPostGISDatastoreEncoder;

import java.io.Serializable;

import org.geojsf.xml.geoserver.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerDataStoreFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerDataStoreFactory.class);
	public static final long serialVersionUID=1;
	
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	public GeoServerDataStoreFactory(GeoServerRESTReader reader, GeoServerRESTPublisher publisher)
	{
		this.reader=reader;
		this.publisher=publisher;
	}
	
	public boolean createDataStore(String workspace, DataStore ds)
	{
		boolean exists = false;
		RESTDataStoreList list = reader.getDatastores(workspace);
		for(String s : list.getNames())
		{
			logger.debug(s);
			if(s.equals(ds.getName())){exists=true;}
		}
		if(!exists){exists=buildDataStore(workspace,ds);}
		return exists;
	}
	
	private boolean buildDataStore(String workspace, DataStore ds)
	{
		GSPostGISDatastoreEncoder pg = new GSPostGISDatastoreEncoder();
		pg.setName(ds.getName());
		pg.setDescription(ds.getDescription());
		pg.setHost(ds.getHost().getName());
		pg.setPort(ds.getHost().getPort());
		pg.setDatabase(ds.getDatabase());
		pg.setSchema(ds.getSchema());
		
		pg.setExposePrimaryKeys(true);
		pg.setMaxConnections(10);
		pg.setMinConnections(1);
		pg.setFetchSize(1000);
		pg.setConnectionTimeout(20);
		pg.setValidateConnections(true);
		pg.setLooseBBox(true);
		pg.setMaxOpenPreparedStatements(50);
		pg.setEnabled(true);
		
		boolean created = publisher.createPostGISDatastore(workspace, pg);
		logger.info("Created DataStore "+created);
		return created;
	}
}