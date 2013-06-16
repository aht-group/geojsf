package org.geojsf.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Layer")
public class Layer  extends UINamingContainer
{	
	final static Logger logger = LoggerFactory.getLogger(Layer.class);
	
	//Define attributes of the component
	private String type   = null;
	private String url    = null;
	private String layers = null;

	public String getType() {
		if (this.type!=null)
		{
			return this.type;
		}
		return "WMS";
	}
	public void setType(String type) {this.type = type;}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	public String getLayers() {return layers;}
	public void setLayers(String layers) {this.layers = layers;}
}
