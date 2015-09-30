package org.geojsf.model.json;

import java.io.Serializable;

public class WmsLayer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private Boolean visible;
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Boolean getVisible() {return visible;}
	public void setVisible(Boolean visible) {this.visible = visible;}
}
