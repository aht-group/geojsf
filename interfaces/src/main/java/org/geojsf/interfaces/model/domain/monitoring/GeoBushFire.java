package org.geojsf.interfaces.model.domain.monitoring;


import org.geojsf.interfaces.util.with.EjbWithGeometry;
import org.jeesl.interfaces.model.with.date.ju.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

import com.vividsolutions.jts.geom.Point;

public interface GeoBushFire extends EjbWithId,EjbWithRecord,EjbWithGeometry<Point>
{

}