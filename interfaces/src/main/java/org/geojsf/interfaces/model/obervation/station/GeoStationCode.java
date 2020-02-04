package org.geojsf.interfaces.model.obervation.station;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoStationCode<L extends JeeslLang, D extends JeeslDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends JeeslStatus<TYPE,L,D>,
							SUBTYPE extends JeeslStatus<SUBTYPE,L,D>,
							SCHEME extends JeeslStatus<SCHEME,L,D>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends JeeslStatus<CAPT,L,D>,
							CAPS extends JeeslStatus<CAPS,L,D>>
			extends EjbWithId,EjbWithCode,EjbWithParentAttributeResolver
{
	public enum Attributes{scheme,station}
	
	STATION getStation();
	void setStation(STATION station);
	
	SCHEME getScheme();
	void setScheme(SCHEME scheme);
	
}