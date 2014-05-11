package org.geojsf.interfaces.model.monitoring;


import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

import org.geojsf.interfaces.model.EjbWithGeometry;

import com.vividsolutions.jts.geom.Point;

public interface GeoBushFire extends EjbWithId,EjbWithRecord,EjbWithGeometry<Point>
{

}