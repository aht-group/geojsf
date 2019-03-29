package org.geojsf.interfaces.model.obervation.station;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCapability<L extends UtilsLang,
							D extends UtilsDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
							SCHEME extends UtilsStatus<SCHEME,L,D>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends UtilsStatus<CAPT,L,D>,
							CAPS extends UtilsStatus<CAPS,L,D>>
			extends Serializable,
						EjbSaveable,EjbRemoveable,
						EjbWithId,EjbWithCode
{
	public enum Attributes{type}
	
	STATION getStation();
	void setStation(STATION station);
	
	CAPS getStatus();
	void setStatus(CAPS status);
	
	CAPT getType();
	void setType(CAPT type);
}