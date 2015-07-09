package org.geojsf.model.pojo.sld;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="SLD Rule",category="sld",subset="sld")
public class DefaultGeoJsfSldRule implements Serializable,EjbRemoveable,EjbPersistable,
				GeoJsfSldRule<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfSldType,DefaultGeoJsfSldStyle,DefaultGeoJsfSld,DefaultGeoJsfSldRule,DefaultGeoJsfSldTemplate>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private DefaultGeoJsfSld sld;
	public DefaultGeoJsfSld getSld(){return sld;}
	public void setSld(DefaultGeoJsfSld sld){this.sld = sld;}
	
	@ManyToOne
	private DefaultGeoJsfSldStyle style;
	public DefaultGeoJsfSldStyle getStyle() {return style;}
	public void setStyle(DefaultGeoJsfSldStyle style) {this.style = style;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
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
	
	private String color;
	@Override public String getColor(){return color;}
	@Override public void setColor(String color){this.color = color;}
	
	private Double lowerBound;
	@Override public Double getLowerBound(){return lowerBound;}
	@Override public void setLowerBound(Double lowerBound){this.lowerBound = lowerBound;}
	
	private Double upperBound;
	@Override public Double getUpperBound(){return upperBound;}
	@Override public void setUpperBound(Double upperBound){this.upperBound = upperBound;}
	
	private Integer size;
	@Override public Integer getSize(){return size;}
	@Override public void setSize(Integer size){this.size = size;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfSldRule) ? id == ((DefaultGeoJsfSldRule) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}