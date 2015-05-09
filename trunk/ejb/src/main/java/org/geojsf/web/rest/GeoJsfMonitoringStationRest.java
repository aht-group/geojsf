package org.geojsf.web.rest;

import net.sf.ahtutils.xml.aht.Aht;

import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationExportRest;
import org.geojsf.model.xml.monitoring.Stations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMonitoringStationRest implements GeoJsfMonitoringStationExportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMonitoringStationRest.class);

	@Override
	public Aht exportGeoJsfMonitoringTypes()
	{
		logger.warn("This needs to be implemented in the core export-service");
		return null;
	}

	@Override
	public Stations exportGeoJsfMonitoringStations()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}