package org.geojsf.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.xml.openlayers.Layers;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Maps;

public interface GeoJsfDatabaseRest
{	
	@GET @Path("/export/services")
	@Produces(MediaType.APPLICATION_XML)
	Repository exportServices();
	
	@GET @Path("/export/layers")
	@Produces(MediaType.APPLICATION_XML)
	Layers exportLayers();
	
	@GET @Path("/export/views")
	@Produces(MediaType.APPLICATION_XML)
	Maps exportViews();
	
	@POST @Path("/import")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	String importGeoJsf(Repository repository);
}
