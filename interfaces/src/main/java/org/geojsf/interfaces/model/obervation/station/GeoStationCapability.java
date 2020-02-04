package org.geojsf.interfaces.model.obervation.station;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCapability<L extends JeeslLang,
							D extends JeeslDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends JeeslStatus<TYPE,L,D>,
							SUBTYPE extends JeeslStatus<SUBTYPE,L,D>,
							SCHEME extends JeeslStatus<SCHEME,L,D>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends JeeslStatus<CAPT,L,D>,
							CAPS extends JeeslStatus<CAPS,L,D>>
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