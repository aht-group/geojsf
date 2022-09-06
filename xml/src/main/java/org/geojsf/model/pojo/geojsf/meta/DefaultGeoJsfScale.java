package org.geojsf.model.pojo.geojsf.meta;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Scale",category="meta",subset="datasource")
public class DefaultGeoJsfScale implements GeoJsfScale<DefaultGeoJsfLang,DefaultGeoJsfDescription>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private int value;
	@Override public int getValue() {return value;}
	@Override public void setValue(int value) {this.value = value;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoScaleJtLang",joinColumns={@JoinColumn(name="scale")},inverseJoinColumns={@JoinColumn(name="lang")})
	private Map<String,DefaultGeoJsfLang> name;
	@Override public Map<String,DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String,DefaultGeoJsfLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoScaleJtDescription",joinColumns={@JoinColumn(name="scale")},inverseJoinColumns={@JoinColumn(name="description")})
	private Map<String,DefaultGeoJsfDescription> description;
	@Override public Map<String,DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,DefaultGeoJsfDescription> description) {this.description = description;}
		
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	@Override public boolean equals(Object object){return (object instanceof DefaultGeoJsfScale) ? id == ((DefaultGeoJsfScale) object).getId() : (object == this);}
}