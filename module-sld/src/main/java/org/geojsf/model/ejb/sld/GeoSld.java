package org.geojsf.model.ejb.sld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
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
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.interfaces.qualifier.er.EjbErNode;
import org.jeesl.model.ejb.io.label.entity.IoLabelAttribute;
import org.jeesl.model.ejb.io.label.entity.IoLabelEntity;
import org.jeesl.model.ejb.io.locale.IoDescription;
import org.jeesl.model.ejb.io.locale.IoLang;

@Entity
@Table(name="GeoSld")
@EjbErNode(name="GeoSld")
public class GeoSld implements GeoJsfSld<IoLang,IoDescription,GeoSldTemplate,GeoSldType,GeoSldRule,IoLabelEntity,IoLabelAttribute>
{	
	public static final long serialVersionUID=1;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	private Boolean library;
	@Override public Boolean getLibrary() {return library;}
	@Override public void setLibrary(Boolean library) {this.library = library;}

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSld_type"))
	private GeoSldType type;
	@Override public GeoSldType getType() {return type;}
	@Override public void setType(GeoSldType type) {this.type = type;}

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSld_template"))
	private GeoSldTemplate template;
	@Override public GeoSldTemplate getTemplate() {return template;}
	@Override public void setTemplate(GeoSldTemplate template) {this.template = template;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoSldJtLang",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldJtLang_sld"), name="sld_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldJtLang_lang"), name="lang_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"lang_id"}, name="uk_GeoSldJtLang_lang"))
	private Map<String,IoLang> name;
	@Override public Map<String,IoLang> getName() {return name;}
	@Override public void setName(Map<String,IoLang> name) {this.name = name;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="GeoSldJtDescription",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldJtDescription_sld"), name="sld_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldJtDescription_lang"), name="description_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"description_id"}, name="uk_GeoSldJtDescription_description"))
	private Map<String,IoDescription> description;
	@Override public Map<String,IoDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,IoDescription> description) {this.description = description;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="GeoSldJtRule",joinColumns={@JoinColumn(name="sld_id")},inverseJoinColumns={@JoinColumn(name="rule_id")})
	@OrderBy("position ASC")
	private List<GeoSldRule> rules;
	@Override public List<GeoSldRule> getRules(){if(Objects.isNull(rules)) {rules = new ArrayList<>();}return rules;}
	@Override public void setRules(List<GeoSldRule> rules){this.rules = rules;}

	private String statusClass;
	@Override public String getStatusClass() {return statusClass;}
	@Override public void setStatusClass(String statusClass) {this.statusClass = statusClass;}

	private String statusAttribute;
	@Override public String getStatusAttribute() {return statusAttribute;}
	@Override public void setStatusAttribute(String statusAttribute) {this.statusAttribute = statusAttribute;}

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSld_entity"))
	private IoLabelEntity entity;
	@Override public IoLabelEntity getEntity() {return entity;}
	@Override public void setEntity(IoLabelEntity entity) {this.entity = entity;}
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSld_attribute"))
	private IoLabelAttribute attribute;
	@Override public IoLabelAttribute getAttribute() {return attribute;}
	@Override public void setAttribute(IoLabelAttribute attribute) {this.attribute = attribute;}


	@Override public boolean equals(Object object) {return (object instanceof GeoSld) ? id == ((GeoSld) object).getId() : (object == this);}
	@Override public int hashCode(){return new HashCodeBuilder(17,37).append(id).toHashCode();}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append("type:");if(type!=null){sb.append(type.getCode());}else{sb.append("null");}

		if(type!=null && type.getCode()!=null && type.getCode().equals(GeoJsfSldType.Type.status.toString()))
		{
			sb.append(" sc:").append(statusClass);
		}
		return sb.toString();
	}
}