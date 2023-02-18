package org.geojsf.model.pojo.geojsf.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.OneToMany;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Category",category="core",level=3,subset="core")
public class DfGeoJsfCategory implements GeoJsfCategory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
		
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@OneToMany
	private List<DfGeoJsfLayer> layer;
	@Override public List<DfGeoJsfLayer> getLayer() {if(layer==null){layer = new ArrayList<DfGeoJsfLayer>();} return layer;}
	@Override public void setLayer(List<DfGeoJsfLayer> layer) {this.layer=layer;}

//	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@MapKey(name = "lkey")
	protected Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {if(name==null){name=new Hashtable<String,DefaultGeoJsfLang>();}return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
//	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@MapKey(name = "lkey")
	protected Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}


	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof DfGeoJsfCategory) ? id == ((DfGeoJsfCategory) object).getId() : (object == this);}
}