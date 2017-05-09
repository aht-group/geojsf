package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface GeoJsfStationFacade <L extends UtilsLang,
										D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>, TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>, SCHEME extends UtilsStatus<SCHEME,L,D>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
										CAPT extends UtilsStatus<CAPT,L,D>,
										CAPS extends UtilsStatus<CAPS,L,D>>
					extends UtilsFacade
{		
	List<STATION> fStations(CAP capability);
}