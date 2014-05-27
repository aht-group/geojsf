package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.OneToMany;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="Map",category="geojsf")
public class DefaultGeoJsfMap implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String code;
	
	private Double lat,lon;
	
	private Integer zoom;
	
	private Map<String, DefaultGeoJsfLang> name;
	
	private Map<String, DefaultGeoJsfDescription> description;
	
	@OneToMany
	private List<DefaultGeoJsfView> layer;
	
	//******************************************************************************
	

	
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@Override public Double getLat() {return lat;}
	@Override public void setLat(Double x) {this.lon = lon;}
	
	@Override public Double getLon() {return lon;}
	@Override public void setLon(Double lon) {this.lon = lon;}
	
	@Override public Integer getZoom() {return zoom;}
	@Override public void setZoom(Integer zoom) {this.zoom = zoom;}
	
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}
	
	@Override public List<DefaultGeoJsfView> getViews() {if(layer==null){layer=new ArrayList<DefaultGeoJsfView>();}return layer;}
	@Override public void setViews(List<DefaultGeoJsfView> layer) {this.layer=layer;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfMap)
             ? id == ((DefaultGeoJsfMap) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}