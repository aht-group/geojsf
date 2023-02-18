package org.geojsf.model.pojo.io.label.revision;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.label.core.DfRevisionEntity;
import org.jeesl.interfaces.model.io.label.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@Table(name="RevisionMappingView",uniqueConstraints = @UniqueConstraint(columnNames = {"view_id","entity_id","entityMapping_id"}))
@EjbErNode(name="View Mapping",category="revision",subset="revision",level=3)
public class DfRevisionViewMapping implements JeeslRevisionViewMapping<DfRevisionView,DfRevisionEntity,DfRevisionEntityMapping>
{
	public static final long serialVersionUID=1;
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private DfRevisionView view;
	@Override public DfRevisionView getView() {return view;}
	@Override public void setView(DfRevisionView view) {this.view = view;}

	@ManyToOne
	private DfRevisionEntity entity;
	@Override public DfRevisionEntity getEntity() {return entity;}
	@Override public void setEntity(DfRevisionEntity entity) {this.entity = entity;}
	
	@ManyToOne
	private DfRevisionEntityMapping entityMapping;
	@Override public DfRevisionEntityMapping getEntityMapping() {return entityMapping;}
	@Override public void setEntityMapping(DfRevisionEntityMapping entityMapping) {this.entityMapping = entityMapping;}

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
	
	@Override public boolean equals(Object object){return (object instanceof DfRevisionViewMapping) ? id == ((DfRevisionViewMapping) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}