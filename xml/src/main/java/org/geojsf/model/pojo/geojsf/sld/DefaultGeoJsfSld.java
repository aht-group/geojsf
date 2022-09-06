package org.geojsf.model.pojo.geojsf.sld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="SLD",category="sld",subset="sld")
public class DefaultGeoJsfSld implements GeoJsfSld<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSldRule>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private Boolean library;
	public Boolean getLibrary() {return library;}
	public void setLibrary(Boolean library) {this.library = library;}

	@ManyToOne
	private DefaultGeoJsfSldType type;
	@Override public DefaultGeoJsfSldType getType() {return type;}
	@Override public void setType(DefaultGeoJsfSldType type) {this.type = type;}
	
	@ManyToOne
	private DefaultGeoJsfSldTemplate template;
	@Override public DefaultGeoJsfSldTemplate getTemplate() {return template;}
	@Override public void setTemplate(DefaultGeoJsfSldTemplate template) {this.template = template;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {if(name==null){name=new Hashtable<String,DefaultGeoJsfLang>();}return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sld")
	@JoinTable(name="GeoSld_Rule",joinColumns={@JoinColumn(name="sld")},inverseJoinColumns={@JoinColumn(name="rule")})
	@OrderBy("position ASC")
	private List<DefaultGeoJsfSldRule> rules;
	@Override public List<DefaultGeoJsfSldRule> getRules(){if(rules==null){rules = new ArrayList<DefaultGeoJsfSldRule>();}return rules;}
	@Override public void setRules(List<DefaultGeoJsfSldRule> rules){this.rules = rules;}
	
	
	private String statusClass;
	@Override public String getStatusClass() {return statusClass;}
	@Override public void setStatusClass(String statusClass) {this.statusClass = statusClass;}
	
	private String statusAttribute;
	@Override public String getStatusAttribute() {return statusAttribute;}
	@Override public void setStatusAttribute(String statusAttribute) {this.statusAttribute = statusAttribute;}

	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof DefaultGeoJsfSld) ? id == ((DefaultGeoJsfSld) object).getId() : (object == this);}
}