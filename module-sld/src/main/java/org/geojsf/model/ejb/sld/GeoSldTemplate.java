package org.geojsf.model.ejb.sld;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.jeesl.interfaces.qualifier.er.EjbErNode;
import org.jeesl.model.ejb.io.locale.IoDescription;
import org.jeesl.model.ejb.io.locale.IoLang;

@Entity
@Table(name="GeoSldTemplate")
@EjbErNode(name="SLD Template",category="station",level=3)
public class GeoSldTemplate implements GeoJsfSldXml<IoLang,IoDescription,GeoSld>
{
	public static final long serialVersionUID=1;

	public static String codePointIntervals = "cblt.sld.point.intervals";

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@OneToOne(fetch=FetchType.EAGER, mappedBy="template")
	private GeoSld sld;
	@Override public GeoSld getSld() {return sld;}
	@Override public void setSld(GeoSld sld) {this.sld = sld;}
	
	@NotNull
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoSldTemplateJtLang",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldTemplateJtLang_sld"), name="template_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRuleJtLang_lang"), name="lang_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"lang_id"}, name="uk_GeoSldTemplateJtLang_lang"))
	private Map<String,IoLang> name;
	@Override public Map<String,IoLang> getName() {if(name==null){name=new HashMap<>();}return name;}
	@Override public void setName(Map<String,IoLang> name) {this.name = name;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoSldTemplateJtDescription",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldTemplateJtDescription_sld"), name="template_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldTemplateJtDescription_lang"), name="description_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"description_id"}, name="uk_GeoSldTemplateJtDescription_description"))
	private Map<String,IoDescription> description;
	@Override public Map<String,IoDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,IoDescription> description) {this.description = description;}

	@Basic @Column(columnDefinition="text")
	private String xml;
	@Override public String getXml() {return xml;}
	@Override public void setXml(String xml) {this.xml = xml;}


	@Override public boolean equals(Object object) {return (object instanceof GeoSldTemplate) ? id == ((GeoSldTemplate) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(41,61).append(id).toHashCode();}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
}