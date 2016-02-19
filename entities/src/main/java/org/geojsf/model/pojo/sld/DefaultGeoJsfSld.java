package org.geojsf.model.pojo.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicType;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="SLD",category="sld",subset="sld")
public class DefaultGeoJsfSld implements Serializable,EjbRemoveable,EjbPersistable,
				GeoJsfSld<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicStyle,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule,DefaultGeoJsfSldTemplate>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private DefaultGeoJsfSldType type;
	public DefaultGeoJsfSldType getType() {return type;}
	public void setType(DefaultGeoJsfSldType type) {this.type = type;}
	
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
	@OrderBy("position ASC")
	private List<DefaultGeoJsfSldRule> rules;
	@Override public List<DefaultGeoJsfSldRule> getRules(){if(rules==null){rules = new ArrayList<DefaultGeoJsfSldRule>();}return rules;}
	@Override public void setRules(List<DefaultGeoJsfSldRule> rules){this.rules = rules;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfSld) ? id == ((DefaultGeoJsfSld) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}