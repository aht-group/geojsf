package org.geojsf.model.pojo.io.label.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.label.revision.DfRevisionEntityMapping;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.qualifier.er.EjbErNode;


@Table(name="RevisionEntity", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
@EjbErNode(name="Entity",category="revision",subset="revision")
public class DfRevisionEntity implements JeeslRevisionEntity<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfRevisionCategory,DfRevisionEntityMapping,DfRevisionAttribute,DfRevisionDiagram>
{
	public static final long serialVersionUID=1;	
	
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String resolveParentAttribute() {return "category";}

	@ManyToOne
	private DfRevisionCategory category;
	public DfRevisionCategory getCategory() {return category;}
	public void setCategory(DfRevisionCategory category) {this.category = category;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private String jscn;
	@Override public String getJscn() {return jscn;}
	@Override public void setJscn(String jscn) {this.jscn = jscn;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	private Boolean timeseries;
	@Override public Boolean getTimeseries() {return timeseries;}
	@Override public void setTimeseries(Boolean timeseries) {this.timeseries = timeseries;}
	
	private Boolean documentation;
	@Override public Boolean getDocumentation() {return documentation;}
	@Override public void setDocumentation(Boolean documentation) {this.documentation = documentation;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoLabelEntityJtLang",joinColumns={@JoinColumn(name="entitiy_id")},inverseJoinColumns={@JoinColumn(name="lang_id")})
	private Map<String,DefaultGeoJsfLang> name;
	@Override public Map<String,DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String,DefaultGeoJsfLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoLabelEntityJtDescription",joinColumns={@JoinColumn(name="entitiy_id")},inverseJoinColumns={@JoinColumn(name="description_id")})
	private Map<String,DefaultGeoJsfDescription> description;
	@Override public Map<String,DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,DefaultGeoJsfDescription> description) {this.description = description;}
	
	@OneToMany(fetch=FetchType.LAZY)
	@OrderBy("position")
	@JoinTable(name="RevisionEntity_Attribute",joinColumns={@JoinColumn(name="entity")},inverseJoinColumns={@JoinColumn(name="attribute")})
	private List<DfRevisionAttribute> attributes;
	@Override public List<DfRevisionAttribute> getAttributes() {if(attributes==null){attributes=new ArrayList<DfRevisionAttribute>();}return attributes;}
	@Override public void setAttributes(List<DfRevisionAttribute> attributes) {this.attributes = attributes;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="entity")
	@OrderBy("position")
	private List<DfRevisionEntityMapping> maps;
	@Override public List<DfRevisionEntityMapping> getMaps() {if(maps==null){maps=new ArrayList<DfRevisionEntityMapping>();}return maps;}
	@Override public void setMaps(List<DfRevisionEntityMapping> maps) {this.maps=maps;}
	
	@Basic @Column(columnDefinition = "text")
	private String developerInfo;
	@Override public String getDeveloperInfo() {return developerInfo;}
	@Override public void setDeveloperInfo(String developerInfo) {this.developerInfo=developerInfo;}
	
	@ManyToOne
	private DfRevisionDiagram diagram;
	@Override public DfRevisionDiagram getDiagram() {return diagram;}
	@Override public void setDiagram(DfRevisionDiagram diagram) {this.diagram = diagram;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof DfRevisionEntity) ? id == ((DfRevisionEntity) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}