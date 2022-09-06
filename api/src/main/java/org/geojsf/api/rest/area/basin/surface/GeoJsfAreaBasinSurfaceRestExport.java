package org.geojsf.api.rest.area.basin.surface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.model.xml.area.Areas;

import net.sf.ahtutils.xml.aht.Aht;

public interface GeoJsfAreaBasinSurfaceRestExport
{	
	@GET @Path("/geojsf/area/basin/surface/model")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportGeoJsfWaterSurfaceBasinModel();
	
	@GET @Path("/geojsf/area/basin/surface")
	@Produces(MediaType.APPLICATION_XML)
	Areas exportGeoJsfWaterSurfaceBasins();
}
