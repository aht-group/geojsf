package org.geojsf.model.pojo.core;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.model.pojo.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldStyle;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="View",category="core",subset="core,datasource")
public class DefaultGeoJsfView implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldType,DefaultGeoJsfSldStyle,DefaultGeoJsfSldTemplate>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private DefaultGeoJsfMap view;
	
	@ManyToOne
	private DefaultGeoJsfLayer layer;
	
	
	private int orderNo;
	
	
	private Boolean visible;
	
	private Boolean legend;
	@Override public Boolean getLegend() {return legend;}
	@Override public void setLegend(Boolean legend) {this.legend=legend;}
	
	//******************************************************************************
	

	
	@Override public DefaultGeoJsfMap getMap() {return view;}
	@Override public void setMap(DefaultGeoJsfMap view) {this.view = view;}
	
	@Override public DefaultGeoJsfLayer getLayer() {return layer;}
	@Override public void setLayer(DefaultGeoJsfLayer layer) {this.layer = layer;}
	
	@Override public int getOrderNo() {return orderNo;}
	@Override public void setOrderNo(int orderNo) {this.orderNo = orderNo;}
	
	@Override public boolean getVisible() {return visible;}
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		return sb.toString();
	}

	@Override
	public int compareTo(DefaultGeoJsfView other)
	{
		return(new Integer(this.getOrderNo())).compareTo(other.getOrderNo());
	}
	
	public boolean equals(Object object){return (object instanceof DefaultGeoJsfView)  ? id == ((DefaultGeoJsfView) object).getId() : (object == this);}
}