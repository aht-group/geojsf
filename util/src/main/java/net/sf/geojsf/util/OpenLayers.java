package net.sf.geojsf.util;

import java.io.FileNotFoundException;
import java.io.Serializable;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.xml.ogc.Distance;
import net.sf.geojsf.xml.openlayers.Repository;
import net.sf.geojsf.xml.openlayers.View;
import net.sf.geojsf.xml.xpath.OpenLayersXpath;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenLayers implements Serializable
{
	static Log logger = LogFactory.getLog(OpenLayers.class);
	
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	private OlWmsLayer wmsLayer;
	private OlClick click;
	private OlSearchRadius searchRadius;
	
	private View view;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Constructor<<<<<<<<<<<<<<<<<<<<<<

	public OpenLayers(Configuration config, String viewCode)
	{
		String layerFile = config.getString("gis/@layerFile");
		logger.trace("Layer File: "+layerFile);
		try
		{
			Repository repository = (Repository)JaxbUtil.loadJAXB(layerFile, Repository.class);
			view = OpenLayersXpath.getView(repository, viewCode);
			wmsLayer = new OlWmsLayer(repository.getLayers(),view);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (ExlpXpathNotFoundException e) {logger.error(e);}
		catch (ExlpXpathNotUniqueException e) {logger.error(e);}
		
		click = new OlClick();
		searchRadius = new OlSearchRadius();
	}
		
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
		
	public OlWmsLayer getWmsLayer() {return wmsLayer;}
	public void setWmsLayer(OlWmsLayer layerOptions) {this.wmsLayer = layerOptions;}
	
	public View getView() {return view;}
	public void setView(View view) {this.view = view;}
	
	public OlClick getClick() {return click;}
	public void setClick(OlClick click) {this.click = click;}
	
	public OlSearchRadius getSearchRadius() {return searchRadius;}
	public void setSearchRadius(OlSearchRadius searchRadius) {this.searchRadius = searchRadius;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public Distance createSearchDistance() {return searchRadius.createSearchDistance(click);}
}
