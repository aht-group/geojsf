package org.geojsf.interfaces.model.monitoring;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

import org.geojsf.interfaces.model.EjbWithGeometry;

import com.vividsolutions.jts.geom.Point;

public interface GeoStation<L extends UtilsLang,
							D extends UtilsDescription,
							CAPABILITY extends UtilsStatus<CAPABILITY,L,D>>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,EjbWithGeometry<Point>
{
	List<CAPABILITY> getCapabilities();
	void setCapabilities(List<CAPABILITY> capabilities);
}