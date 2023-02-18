package org.geojsf.model.pojo.geojsf.sld;

import java.util.Hashtable;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="SLD Template",category="sld",subset="sld")
public class DefaultGeoJsfSldTemplate implements GeoJsfSldXml<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfSld>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="template")
	private DefaultGeoJsfSld sld;
	@Override public DefaultGeoJsfSld getSld() {return sld;}
	@Override public void setSld(DefaultGeoJsfSld sld) {this.sld = sld;}
	
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