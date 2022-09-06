package org.geojsf.api.facade;

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
										TYPE extends JeeslStatus<L,D,TYPE>, SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
										SCHEME extends JeeslStatus<L,D,SCHEME>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends JeeslStatus<L,D,CAPT>,
										CAPS extends JeeslStatus<L,D,CAPS>>
					extends JeeslFacade
{
	STATION load(STATION station);
	
	List<STATION> fStations(CAPT capabilityType);
	List<STATION> fStations(List<CAPT> capabilityTypes);
}