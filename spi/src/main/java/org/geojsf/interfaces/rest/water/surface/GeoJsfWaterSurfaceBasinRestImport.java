package org.geojsf.interfaces.rest.water.surface;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.geojsf.model.xml.area.Areas;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

public interface GeoJsfWaterSurfaceBasinRestImport
{	
	@POST @Path("/geojsf/water/surface/basin/model")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfWaterSurfaceBasinModel(Aht models);
	
	@POST @Path("/geojsf/water/surface/basin")
	@Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfWaterSurfaceBasins(Areas basins);
}
