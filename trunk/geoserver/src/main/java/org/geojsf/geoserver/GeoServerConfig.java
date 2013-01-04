package org.geojsf.geoserver;

public interface GeoServerConfig 
{	
	public static final String dirExport = "geoserver.dir.export";
	
	public static final String dirStyle = "geoserver.style.dir";
	
	public static String workspace = "geoserver.workspace";
	
//ahtutils.highlight:rest
	public static String restHost = "geoserver.rest.host";
	public static String restUser = "geoserver.rest.user";
	public static String restPassword = "geoserver.rest.password";
//ahtutils.highlight:rest	
	
	
	
	public static String dsName = "geoserver.ds.name";
	public static String dsDescription = "geoserver.ds.description";
	public static String dsHost = "geoserver.ds.host";
	public static String dsPort = "geoserver.ds.port";
	public static String dsDatabase = "geoserver.ds.database";
	public static String dsSchema = "geoserver.ds.schema";
	public static String dsUser = "geoserver.ds.user";
	public static String dsPassword = "geoserver.ds.password";
}
