package org.geojsf.interfaces.model.meta;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfEcql <LAYER extends GeoJsfLayer<?,?,?,?,?,?,?,?>,
							LE extends JeeslRevisionEntity<?,?,?,?,LA,?>,
							LA extends JeeslRevisionAttribute<?,?,LE,?,?>>
					extends EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver,
							EjbWithPosition,EjbWithNonUniqueCode
{
	public enum Attributes {layer}
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	LE getEntity();
	void setEntity(LE entity);
	
	LA getAttribute();
	void setAttribute(LA attribute);
	
//	void x();
}