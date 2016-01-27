package org.geojsf.interfaces.model.monitoring;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCapability<L extends UtilsLang,
							D extends UtilsDescription,
							STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>,
							CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>,
							CAPT extends UtilsStatus<CAPT,L,D>,
							CAPS extends UtilsStatus<CAPS,L,D>>
			extends EjbWithId,EjbWithCode
{
	STATION getStation();
	void setStation(STATION station);
	
	CAPS getStatus();
	void setStatus(CAPS status);
	
	CAPT getType();
	void setType(CAPT type);
}