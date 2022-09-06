package org.geojsf.api.rest.db;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;

public interface GeoJsfDatabaseImportRest
{		
//	@POST @Path("/geojsf")
//	@Produces(MediaType.APPLICATION_XML)
//	@Consumes(MediaType.APPLICATION_XML)
//	DataUpdate importGeoJsf(Repository repository, Layers layers, Maps maps);
	
	@POST @Path("/geojsf/services")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfServices(Repository repository);
	
	@POST @Path("/geojsf/categories")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfCategories(Repository categories);
	
	@POST @Path("/geojsf/layer")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfLayers(Layers layers);
	
	@POST @Path("/geojsf/maps")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfMaps(Maps maps);
	
	@POST @Path("/geojsf/sld/type") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfSldTypes(Aht types);
	
	@POST @Path("/geojsf/sld/templates")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfSldTemplates(Repository templates);
}