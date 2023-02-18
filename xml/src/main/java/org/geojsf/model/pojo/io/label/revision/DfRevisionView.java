package org.geojsf.model.pojo.io.label.revision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.io.label.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@Table(name="RevisionView", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
@EjbErNode(name="View",category="revision",subset="revision")
public class DfRevisionView implements JeeslRevisionView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfRevisionViewMapping>
{
	public static final long serialVersionUID=1;
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	
	protected String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="view")
	@OrderBy("position")
	private List<DfRevisionViewMapping> maps;
	@Override public List<DfRevisionViewMapping> getMaps() {if(maps==null){maps=new ArrayList<DfRevisionViewMapping>();}return maps;}
	@Override public void setMaps(List<DfRevisionViewMapping> maps) {this.maps=maps;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoRevisionViewJtLang",joinColumns={@JoinColumn(name="view_id")},inverseJoinColumns={@JoinColumn(name="lang_id")})
	private Map<String,DefaultGeoJsfLang> name;
	@Override public Map<String,DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String,DefaultGeoJsfLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoRevisionViewJtDescription",joinColumns={@JoinColumn(name="view_id")},inverseJoinColumns={@JoinColumn(name="description_id")})
	private Map<String,DefaultGeoJsfDescription> description;
	@Override public Map<String,DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,DefaultGeoJsfDescription> description) {this.description = description;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof DfRevisionView) ? id == ((DfRevisionView) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}