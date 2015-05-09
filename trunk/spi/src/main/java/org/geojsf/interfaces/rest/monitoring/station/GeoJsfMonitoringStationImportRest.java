package org.geojsf.interfaces.rest.monitoring.station;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.model.xml.monitoring.Stations;

public interface GeoJsfMonitoringStationImportRest
{	
	@POST @Path("/geojsf/monitoring/types")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMonitoringCapabilities(Aht capabilities);
	
	@POST @Path("/geojsf/monitoring/stations")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMonitoringStations(Stations stations);
}
