package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.monitoring.GeoStation;
import org.geojsf.interfaces.model.monitoring.GeoStationCapability;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface GeoJsfStationFacade <L extends UtilsLang,
										D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>,
										CAPT extends UtilsStatus<CAPT,L,D>,
										CAPS extends UtilsStatus<CAPS,L,D>>
					extends UtilsFacade
{		
	List<STATION> fStations(CAP capability);
}