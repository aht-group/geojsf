package org.geojsf.model.pojo.geojsf.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.OneToMany;

import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Service",category="core",subset="core")
public class DefaultGeoJsfService implements GeoJsfService<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}

	//	@NotNull
	private String wms;
	@Override public String getWms(){return wms;}
	@Override public void setWms(String wms){this.wms = wms;}

//	@NotNull
	private String wcs;
	@Override public String getWcs(){return wcs;}
	@Override public void setWcs(String wcs){this.wcs = wcs;}
	
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
	
	@Override public boolean equals(Object object) {return (object instanceof DefaultGeoJsfService) ? id == ((DefaultGeoJsfService) object).getId() : (object == this);}
}