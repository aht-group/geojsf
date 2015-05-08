package org.geojsf.interfaces.rest.monitoring.station;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

import org.geojsf.xml.monitoring.Stations;

public interface GeoJsfMonitoringStationExportRest
{	
	@GET @Path("/geojsf/monitoring/types")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportGeoJsfMonitoringTypes();
	
	@GET @Path("/geojsf/monitoring/stations")
	@Produces(MediaType.APPLICATION_XML)
	Stations exportGeoJsfMonitoringStations();
}
