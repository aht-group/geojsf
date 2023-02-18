package org.geojsf.model.pojo.io.label.core;

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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Attribute",category="revision",subset="revision",level=2)
public class DfRevisionAttribute implements JeeslRevisionAttribute<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfRevisionEntity,DfRevisionEntityRelation,DfRevisionAttributeType>
{
	public static final long serialVersionUID=1;


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	//TODO add constraint @NotNull
//	@Transient
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	

	private DfRevisionAttributeType type;
	@Override public DfRevisionAttributeType getType() {return type;}
	@Override public void setType(DfRevisionAttributeType type) {this.type = type;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoLabelAttributeJtLang",joinColumns={@JoinColumn(name="attribute_id")},inverseJoinColumns={@JoinColumn(name="lang_id")})
	private Map<String,DefaultGeoJsfLang> name;
	@Override public Map<String,DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String,DefaultGeoJsfLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="IoLabelAttributeJtDescription",joinColumns={@JoinColumn(name="attribute_id")},inverseJoinColumns={@JoinColumn(name="description_id")})
	private Map<String,DefaultGeoJsfDescription> description;
	@Override public Map<String,DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,DefaultGeoJsfDescription> description) {this.description = description;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private String xpath;
	@Override public String getXpath() {return xpath;}
	@Override public void setXpath(String xpath) {this.xpath = xpath;}

	private boolean showWeb;
	@Override public boolean isShowWeb() {return showWeb;}
	@Override public void setShowWeb(boolean showWeb) {this.showWeb = showWeb;}
	
	private boolean showPrint;
	@Override public boolean isShowPrint() {return showPrint;}
	@Override public void setShowPrint(boolean showPrint) {this.showPrint = showPrint;}
	
	private boolean showName;
	@Override public boolean isShowName() {return showName;}
	@Override public void setShowName(boolean showName) {this.showName = showName;}
	
	private boolean showEnclosure;
	@Override public boolean isShowEnclosure() {return showEnclosure;}
	@Override public void setShowEnclosure(boolean showEnclosure) {this.showEnclosure = showEnclosure;}
	
	private Boolean ui;
	@Override public Boolean getUi() {return ui;}
	@Override public void setUi(Boolean ui) {this.ui=ui;}
	
	private Boolean bean;
	@Override public Boolean getBean() {return bean;}
	@Override public void setBean(Boolean bean) {this.bean=bean;}
	
	private Boolean manualUser;
	@Override public Boolean getManualUser() {return manualUser;}
	@Override public void setManualUser(Boolean manualUser) {this.manualUser = manualUser;}
	
	private Boolean manualAdmin;
	@Override public Boolean getManualAdmin() {return manualAdmin;}
	@Override public void setManualAdmin(Boolean manualAdmin) {this.manualAdmin = manualAdmin;}
	
	private Boolean construction;
	@Override public Boolean getConstruction() {return construction;}
	@Override public void setConstruction(Boolean construction) {this.construction = construction;}
	
	@Basic @Column(columnDefinition = "text")
	private String developerInfo;
	@Override public String getDeveloperInfo() {return developerInfo;}
	@Override public void setDeveloperInfo(String developerInfo) {this.developerInfo=developerInfo;}
	
	@ManyToOne
	private DfRevisionEntityRelation relation;
	@Override public DfRevisionEntityRelation getRelation() {return relation;}
	@Override public void setRelation(DfRevisionEntityRelation relation) {this.relation = relation;}
	
	@ManyToOne
	private DfRevisionEntity entity;
	@Override public DfRevisionEntity getEntity() {return entity;}
	@Override public void setEntity(DfRevisionEntity entity) {this.entity = entity;}
	
	private Boolean relationOwner;
	@Override public Boolean getRelationOwner() {return relationOwner;}
	@Override public void setRelationOwner(Boolean relationOwner) {this.relationOwner = relationOwner;}
	
	private Boolean statusTableDoc;
	@Override public Boolean getStatusTableDoc() {return statusTableDoc;}
	@Override public void setStatusTableDoc(Boolean statusTableDoc) {this.statusTableDoc = statusTableDoc;}
	
	private Boolean docOptionsInline;
	@Override public Boolean getDocOptionsInline() {return docOptionsInline;}
	@Override public void setDocOptionsInline(Boolean docOptionsInline) {this.docOptionsInline = docOptionsInline;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof DfRevisionAttribute) ? id == ((DfRevisionAttribute) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}