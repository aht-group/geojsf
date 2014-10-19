package org.geojsf.interfaces.rest.db;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.ViewPorts;

public interface GeoJsfDatabaseExportRest
{	
	@GET @Path("/geojsf/categories")
	@Produces(MediaType.APPLICATION_XML)
	Repository exportCategories();
	
	@GET @Path("/geojsf/services")
	@Produces(MediaType.APPLICATION_XML)
	Repository exportServices();
	
	@GET @Path("/geojsf/layers")
	@Produces(MediaType.APPLICATION_XML)
	Layers exportLayers();
	
	@GET @Path("/geojsf/views")
	@Produces(MediaType.APPLICATION_XML)
	Maps exportMaps();
	
	@GET @Path("/geojsf/viewPorts")
	@Produces(MediaType.APPLICATION_XML)
	ViewPorts exportViewPorts();
	
//	@POST @Path("/import")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.APPLICATION_XML)
//	String importGeoJsf(Repository repository);
}
