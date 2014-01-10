package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;

import org.geojsf.interfaces.model.GeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;

public class DefaultGeoJsfViewLayer implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfViewLayer<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	
	private DefaultGeoJsfView view;
	private DefaultGeoJsfLayer layer;
	private int orderNo;
	private Boolean visible,legend;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public DefaultGeoJsfView getView() {return view;}
	@Override public void setView(DefaultGeoJsfView view) {this.view = view;}
	
	@Override public DefaultGeoJsfLayer getLayer() {return layer;}
	@Override public void setLayer(DefaultGeoJsfLayer layer) {this.layer = layer;}
	
	@Override public int getOrderNo() {return orderNo;}
	@Override public void setOrderNo(int orderNo) {this.orderNo = orderNo;}
	
	@Override public Boolean isVisible() {return visible;}
	@Override public void setVisible(Boolean visible) {this.visible = visible;}
	
	@Override public Boolean isLegend() {return legend;}
	@Override public void setLegend(Boolean legend) {this.legend=legend;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfViewLayer)
             ? id == ((DefaultGeoJsfViewLayer) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	
}