package org.geojsf.model.pojo.meta;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicType;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Scale",category="meta",subset="datasource")
public class DefaultGeoJsfScale implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfScale<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule>
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