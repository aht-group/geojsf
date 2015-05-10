package org.geojsf.util.query.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfSqlMonitoringQueries
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfSqlMonitoringQueries.class);
	
	private String jtStationCapability;
	private String jtStationLang;
	
	private String tStatus;
	private String tLang;
	
	protected String lang;
	
	public AbstractGeoJsfSqlMonitoringQueries()
	{
		lang="en";
		tStatus = "status";
		tLang = "lang";
		
		jtStationCapability = "JtStationCapability";
		jtStationLang = "station_lang";
	}
	
	protected String stationsId(String inIds){return stations("id", inIds);}
	protected String stationsCode(String inCode){return stations("code", inCode);}
	
	private String stations(String attribute, String inCode)
	{	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT station.id as id\n");
		sb.append("      ,station.code as code\n");
		sb.append("      ,status.id as capid\n");
		sb.append("      ,status.code as capcode\n");
		sb.append("      ,lang.lang as label\n");
		sb.append("      ,station.geom as geom\n");
		sb.append("FROM station station\n");
		sb.append("JOIN ").append(jtStationCapability).append(" cap ON station.id = cap.station_id\n");
		sb.append("JOIN ").append(tStatus).append(" status ON status.id = cap.capabilities_id\n");
		
		sb.append("JOIN ").append(jtStationLang).append(" jtl ON station.id = jtl.station_id\n");
		sb.append("JOIN ").append(tLang).append(" lang ON lang.id = jtl.name_id\n");
		sb.append("WHERE status.").append(attribute).append(" IN (").append(inCode).append(")\n");
		sb.append("  AND lang.lkey = '").append(lang).append("'\n");
		return sb.toString();
	}
}