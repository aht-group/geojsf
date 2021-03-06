package org.geojsf.model.pojo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Map",category="core",subset="core,viewport,datasource")
public class DefaultGeoJsfMap implements GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfView,DefaultGeoJsfViewPort>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private DefaultGeoJsfCategory category;
	@Override public DefaultGeoJsfCategory getCategory(){return category;}
	@Override public void setCategory(DefaultGeoJsfCategory category){this.category = category;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@OneToOne
	private DefaultGeoJsfViewPort viewPort;
	@Override public DefaultGeoJsfViewPort getViewPort(){return viewPort;}
	@Override public void setViewPort(DefaultGeoJsfViewPort viewPort){this.viewPort = viewPort;}
	
	private Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	private Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}
	
	@OneToMany
	private List<DefaultGeoJsfView> layer;
	@Override public List<DefaultGeoJsfView> getViews() {if(layer==null){layer=new ArrayList<DefaultGeoJsfView>();}return layer;}
	@Override public void setViews(List<DefaultGeoJsfView> layer) {this.layer=layer;}
	

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof DefaultGeoJsfMap) ? id == ((DefaultGeoJsfMap) object).getId() : (object == this);}
}