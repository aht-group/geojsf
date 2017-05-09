package org.geojsf.interfaces.model.obervation.station;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCapability<L extends UtilsLang,
							D extends UtilsDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
							SCHEME extends UtilsStatus<SCHEME,L,D>,
							CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
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