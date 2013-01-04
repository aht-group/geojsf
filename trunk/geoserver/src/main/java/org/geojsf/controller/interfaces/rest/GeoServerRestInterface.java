package org.geojsf.controller.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public interface GeoServerRestInterface
{	
	@GET @Path("/styles")
	@Produces(MediaType.APPLICATION_XML)
	String styles();
	
	@GET @Path("/workspaces/{workspace}/styles")
	@Produces(MediaType.APPLICATION_XML)
	String styles(@PathParam("workspace") String workspace);
	
	@GET @Path("/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String style(@PathParam("code") String name);
	
	@GET @Path("/workspaces/{workspace}/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String style(@PathParam("workspace") String workspace, @PathParam("code") String name);
}