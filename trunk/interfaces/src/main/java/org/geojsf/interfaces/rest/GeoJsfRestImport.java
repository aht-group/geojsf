package org.geojsf.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.xml.openlayers.Repository;

public interface GeoJsfRestImport
{	
	@POST @Path("/services")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	String importGeoJsf(Repository repository);
	}
