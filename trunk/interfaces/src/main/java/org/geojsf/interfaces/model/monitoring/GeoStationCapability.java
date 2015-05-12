package org.geojsf.interfaces.model.monitoring;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCapability<L extends UtilsLang,
							D extends UtilsDescription,
							CAPS extends UtilsStatus<CAPS,L,D>,
							CAPT extends UtilsStatus<CAPT,L,D>>
			extends EjbWithId
{
	CAPS getStatus();
	void setStatus(CAPS status);
	
	CAPT getType();
	void setType(CAPT type);
}