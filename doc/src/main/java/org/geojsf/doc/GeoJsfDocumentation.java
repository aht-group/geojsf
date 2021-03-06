package org.geojsf.doc;

import java.io.File;

public interface GeoJsfDocumentation
{
	public static final String geojsfPath = "utils"+File.separator+"geojsf"+File.separator;
	public static final String prefixListingDir = "listing.geojsf";
	
	public static final String entities = "geojsf/msg/admin/entities.xml";
	public static final String entitiesPrefix = "geojsf/msg/admin/entitiesPrefix.xml";
	public static String keyBaseDocDir = "org.geojsf.doc.dir.base";
	
	public static String keyTranslationFile = "net.sf.ahtutils.doc.file.translation";
	public static String keyViews = "net.sf.ahtutils.doc.file.views";
	public static String keyRoles = "net.sf.ahtutils.doc.file.roles";
	
	public static String msgGeoAdmin = "geojsf/msg/admin.xml";
	public static String coordinates = "geojsf/msg/coordinates.xml";
	public static String sld = "geojsf/msg/sld.xml";
	public static String msgMonitoringStation = "geojsf/msg/monitoring/station.xml";
	
	public static final String lstInstGeoserverUser = "admin/installation/geoserver/user.txt";
	public static final String listingInstallGeoserverExtract = "admin/installation/geoserver/extract.txt";
	public static final String lstInstGeoserverSrvPrep = "admin/installation/geoserver/service/preparation.txt";
	public static final String lstInstGeoserverEtcInitD = "admin/installation/geoserver/service/etcInitD.txt";
}