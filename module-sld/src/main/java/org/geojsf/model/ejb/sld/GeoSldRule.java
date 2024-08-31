package org.geojsf.model.ejb.sld;

import java.util.HashMap;
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
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.jeesl.interfaces.qualifier.er.EjbErNode;
import org.jeesl.model.ejb.io.graphic.core.IoGraphic;
import org.jeesl.model.ejb.io.locale.IoDescription;
import org.jeesl.model.ejb.io.locale.IoLang;

@Entity
@Table(name="GeoSldRule")
@EjbErNode(name="SLD Rule",category="station",level=3)
public class GeoSldRule implements GeoJsfSldRule<IoLang,IoDescription,IoGraphic>
{
	public static final long serialVersionUID=1;
	public static enum Code{category};

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name="lkey")
	@JoinTable(name="GeoSldRuleJtLang",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRuleJtLang_sld"), name="rule_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRuleJtLang_lang"), name="lang_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"lang_id"}, name="uk_GeoSldRuleJtLang_lang"))
	private Map<String,IoLang> name;
	@Override public Map<String,IoLang> getName() {if(name==null){name=new HashMap<>();}return name;}
	@Override public void setName(Map<String,IoLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	@JoinTable(name="GeoSldRuleJtDescription",
				joinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRuleJtDescription_sld"), name="rule_id")},
				inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRuleJtDescription_lang"), name="description_id")},
				uniqueConstraints=@UniqueConstraint(columnNames={"description_id"}, name="uk_GeoSldRuleJtDescription_description"))
	private Map<String,IoDescription> description;
	@Override public Map<String,IoDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String,IoDescription> description) {this.description = description;}
		
	private Double lowerBound;
	@Override public Double getLowerBound(){return lowerBound;}
	@Override public void setLowerBound(Double lowerBound){this.lowerBound = lowerBound;}
	
	private Double upperBound;
	@Override public Double getUpperBound(){return upperBound;}
	@Override public void setUpperBound(Double upperBound){this.upperBound = upperBound;}
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(foreignKey=@ForeignKey(name="fk_GeoSldRule_graphic"))
	private IoGraphic graphic;
	public IoGraphic getGraphic() {return graphic;}
	public void setGraphic(IoGraphic graphic) {this.graphic = graphic;}

			
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append(position).append(". ");
		sb.append(" lower: ").append(Objects.nonNull(lowerBound) ? lowerBound : "--");
		sb.append(" upper: ").append(Objects.nonNull(upperBound) ? upperBound : "--");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof GeoSldRule) ? id == ((GeoSldRule) object).getId() : (object == this);}
	@Override public int hashCode(){return new HashCodeBuilder(17,37).append(id).toHashCode();}
}