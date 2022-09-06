package org.geojsf.model.pojo.geojsf.meta;

import java.io.Serializable;

import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="ViewPort",category="meta",subset="viewport")
public class DefaultGeoJsfViewPort implements Serializable,EjbRemoveable,EjbPersistable,GeoJsfViewPort
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private double lat;
	@Override public double getLat(){return lat;}
	@Override public void setLat(double lat){this.lat = lat;}
	
	private double lon;
	@Override public double getLon(){return lon;}
	@Override public void setLon(double lon){this.lon = lon;}
	
	private int scale;
	@Override public int getScale(){return scale;}
	@Override public void setScale(int scale){this.scale = scale;}
	
	private double marginLeft;
	@Override public double getMarginLeft() {return marginLeft;}
	@Override public void setMarginLeft(double marginLeft) {this.marginLeft=marginLeft;}
	
	private double marginRight;
	@Override public double getMarginRight() {return marginRight;}
	@Override public void setMarginRight(double marginRight) {this.marginRight=marginRight;}
	
	private double marginTop;
	@Override public double getMarginTop() {return marginTop;}
	@Override public void setMarginTop(double marginTop) {this.marginTop=marginTop;}
	
	private double marginBottom;
	@Override public double getMarginBottom() {return marginBottom;}
	@Override public void setMarginBottom(double marginBottom) {this.marginBottom=marginBottom;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfViewPort) ? id == ((DefaultGeoJsfViewPort) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	
}