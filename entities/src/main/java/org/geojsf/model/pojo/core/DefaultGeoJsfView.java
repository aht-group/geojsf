package org.geojsf.model.pojo.core;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.model.pojo.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.meta.DefaultGeoJsfScale;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicFigure;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicType;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="View",category="core",subset="core,datasource")
public class DefaultGeoJsfView implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicFigure,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule>
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
	@Override public DefaultGeoJsfLayer getLayer() {return layer;}
	@Override public void setLayer(DefaultGeoJsfLayer layer) {this.layer = layer;}
	
	
	private int orderNo;
	@Override public int getOrderNo() {return orderNo;}
	@Override public void setOrderNo(int orderNo) {this.orderNo = orderNo;}
	
	private Integer legendNo;
	@Override public Integer getLegendNo() {return legendNo;}
	@Override public void setLegendNo(Integer legendNo) {this.legendNo = legendNo;}
	
	private Boolean visible;
	
	private Boolean legend;
	@Override public Boolean getLegend() {return legend;}
	@Override public void setLegend(Boolean legend) {this.legend=legend;}
	
	private Boolean legendSymbol;
	@Override public Boolean getLegendSymbol() {return legendSymbol;}
	@Override public void setLegendSymbol(Boolean legendSymbol) {this.legendSymbol = legendSymbol;}
	
	@Override public DefaultGeoJsfMap getMap() {return view;}
	@Override public void setMap(DefaultGeoJsfMap view) {this.view = view;}
	

	

	
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