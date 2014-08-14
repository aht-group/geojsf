package org.geojsf.component.entities;

import java.io.Serializable;
import java.util.Hashtable;

public class OlService implements Serializable {
	
	private static final long serialVersionUID = 6027640604576855291L;
	private long id;
	private String code;
	private String url;
	private Hashtable<Long, Boolean> layerVisibility;
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url=url;}
	
	public Boolean isLayerVisible(Long layerId) {return layerVisibility.get(layerId);}
	public void setLayerVisibility(Hashtable<Long, Boolean> layerVisibility) {this.layerVisibility = layerVisibility;}
	public Hashtable<Long, Boolean> getLayerVisibility() {return layerVisibility;}
	public void setVisibility(Long layerId, Boolean visibility)
	{
		this.getLayerVisibility().put(layerId, visibility);
	}
}
