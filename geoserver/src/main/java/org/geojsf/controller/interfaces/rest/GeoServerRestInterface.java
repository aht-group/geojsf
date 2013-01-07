package org.geojsf.controller.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.geojsf.xml.geoserver.Workspace;

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
	
	@DELETE @Path("/workspaces/{workspace}/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String delete(@PathParam("workspace") String workspace, @PathParam("code") String name, @QueryParam("purge") boolean purge);
	
	@DELETE @Path("/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String delete(@PathParam("code") String name, @HeaderParam("purge") boolean purge);
	
	@POST @Path("/workspaces/{workspace}/styles")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes("application/vnd.ogc.sld+xml")
	String updateStyle(@PathParam("workspace") String workspace, String sld);
	
	// WORKSPACE
	@GET @Path("/workspaces")
	@Produces(MediaType.APPLICATION_XML)
	String workspaces();
	
	@POST @Path("/workspaces.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createWorkspace(String workspace);
	
	// DATASTORE
	@GET @Path("/workspaces/{workspace}/datastores")
	@Produces(MediaType.APPLICATION_XML)
	String dataStores(@PathParam("workspace") String workspace);
	
}