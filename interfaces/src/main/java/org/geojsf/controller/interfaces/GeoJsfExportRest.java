package org.geojsf.controller.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.xml.openlayers.Layers;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Views;


public interface GeoJsfExportRest
{	
	@GET @Path("/services")
	@Produces(MediaType.APPLICATION_XML)
	Repository exportServices();
	
	@GET @Path("/layers")
	@Produces(MediaType.APPLICATION_XML)
	Layers exportLayers();
	
	@GET @Path("/views")
	@Produces(MediaType.APPLICATION_XML)
	Views exportViews();
}
