package org.geojsf.interfaces.model.meta;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;

public interface GeoJsfEcql <LAYER extends GeoJsfLayer<?,?,?,?,?,?,?,?>,
							LE extends JeeslRevisionEntity<?,?,?,?,LA,?>,
							LA extends JeeslRevisionAttribute<?,?,LE,?,?>>
					extends Serializable,EjbRemoveable,EjbWithParentAttributeResolver,
							EjbWithPosition,EjbWithNonUniqueCode
{
	public enum Attributes {layer}
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	LE getEntity();
	void setEntity(LE entity);
	
	LA getAttribute();
	void setAttribute(LA attribute);
}