package org.geojsf.api.rest.area.basin.surface;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.model.xml.area.Areas;

import org.jeesl.model.xml.io.ssi.sync.DataUpdate;

public interface GeoJsfAreaBasinSurfaceRestImport
{	
	@POST @Path("/geojsf/area/basin/surface")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfAreaBasinSurface(Areas basins);
}
