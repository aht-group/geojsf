package org.geojsf.interfaces.rest.water.surface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

import org.geojsf.model.xml.water.Basins;

public interface GeoJsfWaterSurfaceBasinRestExport
{	
	@GET @Path("/geojsf/water/surface/basin/model")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportGeoJsfWaterSurfaceBasinModel();
	
	@GET @Path("/geojsf/water/surface/basin")
	@Produces(MediaType.APPLICATION_XML)
	Basins exportGeoJsfWaterSurfaceBasins();
}
