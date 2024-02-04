package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.api.rest.geoserver.GeoServerLayerRest;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.model.xml.geoserver.CoverageStore;
import org.geojsf.model.xml.geoserver.FeatureType;
import org.geojsf.model.xml.geoserver.Layer;
import org.geojsf.model.xml.geoserver.Layers;
import org.geojsf.model.xml.geoserver.Style;
import org.geojsf.model.xml.geoserver.Workspace;
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
		
		JaxbUtil.trace(csManager.getCoverageStores(ws));
		
		Layers allLayers = rest.allLayers();
		Layers result = new Layers();
		for(Layer layer : allLayers.getLayer())
		{
			JaxbUtil.trace(layer);
			layer = rest.getLayer(layer.getName());
			if(Objects.nonNull(layer.getCoverageStore()) && setCs.contains(layer.getCoverageStore().getName()))
			{
				result.getLayer().add(layer);
			}
			else if(Objects.nonNull(layer.getFeatureType()) && setFt.contains(layer.getFeatureType().getName()))
			{
				result.getLayer().add(layer);
			}
			else
			{
				logger.warn("Unknown handling for layer "+layer.getName());
				JaxbUtil.trace(layer);
			}
			JaxbUtil.trace(layer);
			
		}
		return result;
	}
	
	public void updateLayer(Workspace workspace, Layer layer)
	{
		logger.info("update Layer "+layer.getName());
		layer.getStyle().setWorkspace(workspace);
		if(Objects.nonNull(layer.getStyles())) {for(Style style : layer.getStyles().getStyle()){style.setWorkspace(workspace);}}
		layer.setCoverageStore(null);
		rest.updateLayer(layer);
	}
}