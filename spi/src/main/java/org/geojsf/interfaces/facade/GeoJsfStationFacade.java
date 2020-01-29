package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.jeesl.interfaces.facade.JeeslFacade;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface GeoJsfStationFacade <L extends UtilsLang, D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
										SCHEME extends UtilsStatus<SCHEME,L,D>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends UtilsStatus<CAPT,L,D>,
										CAPS extends UtilsStatus<CAPS,L,D>>
					extends JeeslFacade
{
	STATION load(STATION station);
	
	List<STATION> fStations(CAPT capabilityType);
	List<STATION> fStations(List<CAPT> capabilityTypes);
}