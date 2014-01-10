package org.geojsf.util.wfs;

import java.io.Serializable;

import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.openlayers.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenLayers implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(OpenLayers.class);
	
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	private OlWmsLayer wmsLayer;
	private OlClick click;
	private OlSearchRadius searchRadius;
	
	private Map view;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Constructor<<<<<<<<<<<<<<<<<<<<<<

	public OpenLayers(String viewCode)
	{
		view = new Map();
		view.setCode("myCode");
		
		click = new OlClick();
		click.setLon("10.0");
		click.setLat("10.0");
		click.setResolution("2");
		searchRadius = new OlSearchRadius();
	}
		
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
		
	public OlWmsLayer getWmsLayer() {return wmsLayer;}
	public void setWmsLayer(OlWmsLayer layerOptions) {this.wmsLayer = layerOptions;}
	
	public Map getView() {return view;}
	public void setView(Map view) {this.view = view;}
	
	public OlClick getClick() {return click;}
	public void setClick(OlClick click) {this.click = click;}
	
	public OlSearchRadius getSearchRadius() {return searchRadius;}
	public void setSearchRadius(OlSearchRadius searchRadius) {this.searchRadius = searchRadius;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public Distance createSearchDistance() {return searchRadius.createSearchDistance(click);}
}
