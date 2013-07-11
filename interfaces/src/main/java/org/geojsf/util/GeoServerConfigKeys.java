package org.geojsf.util;

public interface GeoServerConfigKeys 
{	
	public static final String dirExport = "geoserver.dir.export";
	public static final String dirImport = "geoserver.dir.import";
	
	public static final String dirStyle = "geoserver.style.dir";

//ahtutils.highlight:keys	
	public static String workspace = "geoserver.workspace";
	
	public static String restUrl = "geoserver.rest.url";
	public static String restUser = "geoserver.rest.user";
	public static String restPassword = "geoserver.rest.password";
//ahtutils.highlight:keys
	
	public static String dsName = "geoserver.ds.name";
	public static String dsDescription = "geoserver.ds.description";
	public static String dsHost = "geoserver.ds.host";
	public static String dsPort = "geoserver.ds.port";
	public static String dsDatabase = "geoserver.ds.database";
	public static String dsSchema = "geoserver.ds.schema";
	public static String dsUser = "geoserver.ds.user";
	public static String dsPassword = "geoserver.ds.password";
}