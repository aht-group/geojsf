package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;

public class DefaultGeoJsfView implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	
	private String code;
	
	private Double x,y;
	
	private Integer zoom;
	
	private Map<String, DefaultGeoJsfLang> name;
	
	private Map<String, DefaultGeoJsfDescription> description;
	
	private List<DefaultGeoJsfViewLayer> layer;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@Override public Double getX() {return x;}
	@Override public void setX(Double x) {this.x = x;}
	
	@Override public Double getY() {return y;}
	@Override public void setY(Double y) {this.y = y;}
	
	@Override public Integer getZoom() {return zoom;}
	@Override public void setZoom(Integer zoom) {this.zoom = zoom;}
	
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}
	
	@Override public List<DefaultGeoJsfViewLayer> getLayer() {if(layer==null){layer=new ArrayList<DefaultGeoJsfViewLayer>();}return layer;}
	@Override public void setLayer(List<DefaultGeoJsfViewLayer> layer) {this.layer=layer;}
	
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