package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="ViewPort",category="geojsf")
public class DefaultGeoJsfViewPort implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfViewPort<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort>
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
	
	private double scale;
	@Override public double getScale(){return scale;}
	@Override public void setScale(double scale){this.scale = scale;}
	
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