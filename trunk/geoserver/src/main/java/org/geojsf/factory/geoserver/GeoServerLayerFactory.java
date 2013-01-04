package org.geojsf.factory.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;

import java.io.Serializable;

import org.geojsf.xml.geoserver.Layer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerLayerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoServerLayerFactory.class);
	public static final long serialVersionUID=1;
	
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	
	public GeoServerLayerFactory(GeoServerRESTReader reader, GeoServerRESTPublisher publisher)
	{
		this.reader=reader;
		this.publisher=publisher;
	}
	
	public boolean createLayer(String workspace, String ds, Layer layer)
	{
		
		return false;
	}
	
	private boolean createDbLayer(String workspace, String ds, Layer layer)
	{
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		fte.setName(layer.getName());
		
		GSLayerEncoder layerEncoder = new GSLayerEncoder();
		layerEncoder.setEnabled(true);

		return publisher.publishDBLayer(workspace, ds, fte, layerEncoder);
	}
	
	

}
