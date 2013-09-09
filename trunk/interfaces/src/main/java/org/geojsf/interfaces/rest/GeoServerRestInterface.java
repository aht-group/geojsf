package org.geojsf.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public interface GeoServerRestInterface
{	
	//STYLE
	@GET @Path("/styles")
	@Produces(MediaType.APPLICATION_XML)
	String styles();
	
	@GET @Path("/workspaces/{workspace}/styles")
	@Produces(MediaType.APPLICATION_XML)
	String styles(@PathParam("workspace") String workspace);
	
	@GET @Path("/styles/{style}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String style(@PathParam("style") String name);
	
	@GET @Path("/workspaces/{workspace}/styles/{style}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String style(@PathParam("workspace") String workspace, @PathParam("style") String style);
	
	@DELETE @Path("/workspaces/{workspace}/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String delete(@PathParam("workspace") String workspace, @PathParam("code") String name, @QueryParam("purge") boolean purge);
	
	@DELETE @Path("/styles/{code}.sld")
	@Produces(MediaType.APPLICATION_XML)
	String delete(@PathParam("code") String name, @HeaderParam("purge") boolean purge);
	
	@POST @Path("/workspaces/{workspace}/styles.sld")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes("application/vnd.ogc.sld+xml")
	String createStyle(@PathParam("workspace") String workspace, String sld);
	
	// WORKSPACE
	@GET @Path("/workspaces")
	@Produces(MediaType.APPLICATION_XML)
	String workspaces();
	
	@GET @Path("/workspaces/{workspace}.xml")
	@Produces(MediaType.APPLICATION_XML)
	String workspace(@PathParam("workspace") String workspace);
	
	@POST @Path("/workspaces.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createWorkspace(String workspace);
	
	@GET @Path("/namespaces")
	@Produces(MediaType.APPLICATION_XML)
	String namespaces();
	
	@GET @Path("/namespaces/{workspace}.xml")
	@Produces(MediaType.APPLICATION_XML)
	String namespace(@PathParam("workspace") String workspace);
	
	@POST @Path("/namespaces.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createNamespace(String namespace);
	
	// DATASTORE
	@GET @Path("/workspaces/{workspace}/datastores")
	@Produces(MediaType.APPLICATION_XML)
	String dataStores(@PathParam("workspace") String workspace);
	
	@GET @Path("/workspaces/{workspace}/datastores/{datastore}")
	@Produces(MediaType.APPLICATION_XML)
	String dataStore(@PathParam("workspace") String workspace, @PathParam("datastore") String datastore);
	
	@POST @Path("/workspaces/{workspace}/datastores.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createDatastore(@PathParam("workspace") String workspace,String ds);
	
	// COVERAGESTORE
	@GET @Path("/workspaces/{workspace}/coveragestores")
	@Produces(MediaType.APPLICATION_XML)
	String coverageStores(@PathParam("workspace") String workspace);
	
	@GET @Path("/workspaces/{workspace}/coveragestores/{coveragestore}.xml")
	@Produces(MediaType.APPLICATION_XML)
	String coverageStore(@PathParam("workspace") String workspace, @PathParam("coveragestore") String coveragestore);
	
	@POST @Path("/workspaces/{workspace}/coveragestores.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createCoverageStore(@PathParam("workspace") String workspace, String cs);
	
	// COVERAGES
	@GET @Path("/workspaces/{workspace}/coveragestores/{coveragestore}/coverages.xml")
	@Produces(MediaType.APPLICATION_XML)
	String coverages(@PathParam("workspace") String workspace, @PathParam("coveragestore") String coveragestore);
	
	@GET @Path("/workspaces/{workspace}/coveragestores/{coveragestore}/coverages/{coverage}.xml")
	@Produces(MediaType.APPLICATION_XML)
	String coverage(@PathParam("workspace") String workspace, @PathParam("coveragestore") String coveragestore, @PathParam("coverage") String coverage);
	
	@POST @Path("/workspaces/{workspace}/coveragestores/{coveragestore}/coverages.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String createCoverage(@PathParam("workspace") String workspace, @PathParam("coveragestore") String coveragestore, String coverage);
	
	// LAYER
	@GET @Path("/layers")
	@Produces(MediaType.APPLICATION_XML)
	String getLayers();
	
	@GET @Path("/layers/{layer}.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String layer(@PathParam("layer") String layer);
	
	@PUT @Path("/layers/{layer}.xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	String updatelayer(@PathParam("layer") String layer, String xml);
	
	//FEATURE TYPE
	@GET @Path("/workspaces/{workspace}/datastores/{datastore}/featuretypes.xml")
	@Produces(MediaType.APPLICATION_XML)
	String featureTypes(@PathParam("workspace") String workspace, @PathParam("datastore") String datastore);
		
	@GET @Path("/workspaces/{workspace}/datastores/{datastore}/featuretypes/{featuretype}.xml")
	@Produces(MediaType.APPLICATION_XML)
	String featureType(@PathParam("workspace") String workspace, @PathParam("datastore") String datastore, @PathParam("featuretype") String featuretype);
}