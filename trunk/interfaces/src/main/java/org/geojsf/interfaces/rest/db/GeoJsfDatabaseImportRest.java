package org.geojsf.interfaces.rest.db;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.xml.geojsf.Repository;

public interface GeoJsfDatabaseImportRest
{		
	@POST @Path("/geojsf")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsf(Repository repository);
}
