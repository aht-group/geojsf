package org.geojsf.interfaces.model.with.container;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface EjbWithPolygonContainer <P extends EjbWithGeometry<MultiPolygon>> extends EjbWithId
{
	P getPolygon();
	void setPolygon(P polygon);
}