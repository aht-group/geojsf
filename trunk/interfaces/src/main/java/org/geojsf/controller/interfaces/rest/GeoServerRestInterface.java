package org.geojsf.controller.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public interface GeoServerRestInterface
{	
	@GET @Path("/styles")
	@Produces(MediaType.APPLICATION_XML)
	String styles();
}
