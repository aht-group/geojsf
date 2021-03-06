package org.geojsf.interfaces.rest.db;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Container;
import net.sf.ahtutils.xml.sync.DataUpdate;

public interface GeoJsfDatabaseImportRest2
{		
	//Will be merged to GeoJsfDbImportRest
	
	@POST @Path("/geojsf/type/multipolygon") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importGeoJsfTypesMultipolygon(Container types);
}