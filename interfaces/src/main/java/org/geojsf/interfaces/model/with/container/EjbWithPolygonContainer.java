package org.geojsf.interfaces.model.with.container;

import org.geojsf.interfaces.model.with.EjbWithGeometry;

import com.vividsolutions.jts.geom.MultiPolygon;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithPolygonContainer <P extends EjbWithGeometry<MultiPolygon>> extends EjbWithId
{
	P getPolygon();
	void setPolygon(P polygon);
}