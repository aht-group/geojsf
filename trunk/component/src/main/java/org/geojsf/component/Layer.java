package org.geojsf.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Layer")
public class Layer  extends UINamingContainer
{	
	final static Logger logger = LoggerFactory.getLogger(Layer.class);
	
	//Define attributes of the component
	private String name              = null;
	private String type              = null;
	private String url               = null;
	private String layers            = null;
	private Boolean isBaseLayer      = false;
	private Boolean hasTemporalLayer = false;

	public String getType() {
		if (this.type!=null)
		{
			return this.type;
		}
		return "WMS";
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		logger.info("Adding "+this.getType() +" layer with data from " +this.getUrl() +" displaying layers " +this.layers);
		if (isBaseLayer) {logger.info("... defined as Base Layer.");}
	}
	
	
	public void setType(String type) {this.type = type;}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	public String getLayers() {return layers;}
	public void setLayers(String layers) {this.layers = layers;}
	
	
	public Boolean getIsBaseLayer() {return isBaseLayer;}
	public void setIsBaseLayer(Boolean isBaseLayer) {this.isBaseLayer = isBaseLayer;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHasTemporalLayer() {
		return hasTemporalLayer;
	}

	public void setHasTemporalLayer(Boolean hasTemporalLayer) {
		this.hasTemporalLayer = hasTemporalLayer;
	}
}
