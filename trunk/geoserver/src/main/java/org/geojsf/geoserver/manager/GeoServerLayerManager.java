package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.interfaces.rest.GeoServerRest;
import org.geojsf.interfaces.rest.geoserver.GeoServerLayerRest;
import org.geojsf.xml.geoserver.CoverageStore;
import org.geojsf.xml.geoserver.FeatureType;
import org.geojsf.xml.geoserver.Layer;
import org.geojsf.xml.geoserver.Layers;
import org.geojsf.xml.geoserver.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerLayerManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerLayerManager.class);
	
	public static String xml = "layers.xml";
	
	private GeoServerLayerRest rest;
	private GeoServerCoverageManager csManager;
	private GeoServerFeatureTypeManager ftManager;

	public GeoServerLayerManager(GeoServerRest rest)
	{
		this.rest=rest;
		csManager = new GeoServerCoverageManager(rest);
		ftManager = new GeoServerFeatureTypeManager(rest);
	}
	
	public Layers getLayer(Workspace ws) throws IOException
	{
		Set<String> setFt = new HashSet<String>();
		for(FeatureType ft : ftManager.getFeatureTypes(ws).getFeatureType()){setFt.add(ft.getName());}
		
		Set<String> setCs = new HashSet<String>();
		for(CoverageStore cs : csManager.getCoverageStores(ws).getCoverageStore()){setCs.add(cs.getName());}
		
		Layers allLayers = rest.allLayers();
		Layers result = new Layers();
		for(Layer layer : allLayers.getLayer())
		{
			layer = rest.getLayer(layer.getName());
			if(layer.isSetCoverageStore() && setCs.contains(layer.getCoverageStore().getName()))
			{
				result.getLayer().add(layer);
			}
			else if(layer.isSetFeatureType() && setFt.contains(layer.getFeatureType().getName()))
			{
				result.getLayer().add(layer);
			}
			else
			{
				logger.warn("Unknown handling for layer "+layer.getName());
			}
			JaxbUtil.trace(layer);
			
		}
		return result;
	}
}