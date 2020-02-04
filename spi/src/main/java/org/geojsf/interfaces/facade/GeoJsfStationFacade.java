package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface GeoJsfStationFacade <L extends JeeslLang, D extends JeeslDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends JeeslStatus<TYPE,L,D>, SUBTYPE extends JeeslStatus<SUBTYPE,L,D>,
										SCHEME extends JeeslStatus<SCHEME,L,D>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends JeeslStatus<CAPT,L,D>,
										CAPS extends JeeslStatus<CAPS,L,D>>
					extends JeeslFacade
{
	STATION load(STATION station);
	
	List<STATION> fStations(CAPT capabilityType);
	List<STATION> fStations(List<CAPT> capabilityTypes);
}