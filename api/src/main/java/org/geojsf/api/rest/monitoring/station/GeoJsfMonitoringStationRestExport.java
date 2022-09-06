package org.geojsf.api.rest.monitoring.station;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

import org.geojsf.model.xml.monitoring.Stations;

public interface GeoJsfMonitoringStationRestExport
{	
	@GET @Path("/geojsf/monitoring/capability/types")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportGeoJsfMonitoringCapabilityTypes();
	
	@GET @Path("/geojsf/monitoring/capability/status")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportGeoJsfMonitoringCapabilityStatus();
	
	@GET @Path("/geojsf/monitoring/stations")
	@Produces(MediaType.APPLICATION_XML)
	Stations exportGeoJsfMonitoringStations();
}
