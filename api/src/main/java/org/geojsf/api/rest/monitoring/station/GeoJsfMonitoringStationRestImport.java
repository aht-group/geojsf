package org.geojsf.api.rest.monitoring.station;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import org.jeesl.model.xml.io.ssi.sync.DataUpdate;

import org.geojsf.model.xml.monitoring.Stations;

public interface GeoJsfMonitoringStationRestImport
{	
	@POST @Path("/geojsf/monitoring/capability/types")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMonitoringCapabilityTypes(Aht types);
	
	@POST @Path("/geojsf/monitoring/capability/status")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMonitoringCapabilityStatus(Aht statuses);
	
	@POST @Path("/geojsf/monitoring/stations")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMonitoringStations(Stations stations);
}
