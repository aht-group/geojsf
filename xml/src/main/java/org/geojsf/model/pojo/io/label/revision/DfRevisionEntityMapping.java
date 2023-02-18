package org.geojsf.model.pojo.io.label.revision;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.label.core.DfRevisionEntity;
import org.jeesl.interfaces.model.io.label.revision.core.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@Table(name="RevisionMappingEntity",uniqueConstraints = @UniqueConstraint(columnNames = {"entity_id","scope_id"}))
@EjbErNode(name="Entity Mapping",category="revision",subset="revision",level=3)
public class DfRevisionEntityMapping implements JeeslRevisionEntityMapping<DfRevisionScope,DfRevisionScopeType,DfRevisionEntity>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	 @ManyToOne
	private DfRevisionEntity entity;
	@Override public DfRevisionEntity getEntity() {return entity;}
	@Override public void setEntity(DfRevisionEntity entity) {this.entity = entity;}
	
	 @ManyToOne
	private DfRevisionScope scope;
	@Override public DfRevisionScope getScope() {return scope;}
	@Override public void setScope(DfRevisionScope scope) {this.scope = scope;}
	
//	@NotNull
	@ManyToOne
	private DfRevisionScopeType type;
	@Override public DfRevisionScopeType getType() {return type;}
	@Override public void setType(DfRevisionScopeType type) {this.type = type;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private String xpath;
	@Override public String getXpath() {return xpath;}
	@Override public void setXpath(String xpath) {this.xpath = xpath;}
	
	private String jpqlTree;
	@Override public String getJpqlTree() {return jpqlTree;}
	@Override public void setJpqlTree(String jpqlTree) {this.jpqlTree = jpqlTree;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof DfRevisionEntityMapping) ? id == ((DfRevisionEntityMapping) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}