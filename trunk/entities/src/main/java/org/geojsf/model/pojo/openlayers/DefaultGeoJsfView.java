package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="View",category="geojsf")
public class DefaultGeoJsfView implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	
	@ManyToOne
	private DefaultGeoJsfMap view;
	
	@ManyToOne
	private DefaultGeoJsfLayer layer;
	
	
	private int orderNo;
	private Boolean visible,legend;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public DefaultGeoJsfMap getMap() {return view;}
	@Override public void setMap(DefaultGeoJsfMap view) {this.view = view;}
	
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
        return (object instanceof DefaultGeoJsfView)
             ? id == ((DefaultGeoJsfView) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	
}