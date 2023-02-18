package org.geojsf.interfaces.model.domain.station;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface GeoStationCapability<L extends JeeslLang,
							D extends JeeslDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends JeeslStatus<L,D,TYPE>,
							SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
							SCHEME extends JeeslStatus<L,D,SCHEME>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends JeeslStatus<L,D,CAPT>,
							CAPS extends JeeslStatus<L,D,CAPS>>
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