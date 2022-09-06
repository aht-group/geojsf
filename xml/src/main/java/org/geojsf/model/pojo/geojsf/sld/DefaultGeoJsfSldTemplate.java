package org.geojsf.model.pojo.geojsf.sld;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.ManyToOne;

import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="SLD Template",category="sld",subset="sld")
public class DefaultGeoJsfSldTemplate implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfSldTemplate<DefaultGeoJsfLang,DefaultGeoJsfDescription>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@ManyToOne
	private DefaultGeoJsfSldType type;
	public DefaultGeoJsfSldType getType() {return type;}
	public void setType(DefaultGeoJsfSldType type) {this.type = type;}
	
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

	private String xml;
	public String getXml() {return xml;}
	public void setXml(String xml) {this.xml = xml;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	public boolean equals(Object object){return (object instanceof DefaultGeoJsfSldTemplate) ? id == ((DefaultGeoJsfSldTemplate) object).getId() : (object == this);}

	
}