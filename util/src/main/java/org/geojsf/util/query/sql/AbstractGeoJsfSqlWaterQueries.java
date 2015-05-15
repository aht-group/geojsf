package org.geojsf.util.query.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfSqlWaterQueries
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfSqlWaterQueries.class);
	
	private String tBasin;
	private String tStatus;
	private String tLang;
	
	private String jtLang,jtStatusLang;
	
	protected String lang;
	
	public AbstractGeoJsfSqlWaterQueries()
	{
		lang="en";
		
		tBasin = "basinsurface";
		tStatus = "status";
		tLang = "lang";
		
		jtLang = tBasin+"_"+tLang;
		jtStatusLang = tStatus+"_"+tLang;
	}
	
	protected String basinsModelCode(String inCode){return stations("code", inCode);}
	
	private String stations(String attribute, String inCode)
	{	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT basin.id as id\n");
		sb.append("      ,status.code as modelCode\n");
		sb.append("      ,slang.lang as modelLabel\n");
		
		sb.append("      ,lang.lang as label\n");
		sb.append("      ,basin.geom as geom\n");
		sb.append("FROM ").append(tBasin).append(" basin\n");
		sb.append("JOIN ").append(tStatus).append(" status ON status.id = basin.model_id\n");
		sb.append("JOIN ").append(jtStatusLang).append(" jtsl ON status.id = jtsl.status_id\n");
		sb.append("JOIN ").append(tLang).append(" slang ON slang.id = jtsl.name_id\n");
		
		sb.append("JOIN ").append(jtLang).append(" jtl ON basin.id = jtl.").append(tBasin).append("_id\n");
		sb.append("JOIN ").append(tLang).append(" lang ON lang.id = jtl.name_id\n");
		sb.append("WHERE status.").append(attribute).append(" IN (").append(inCode).append(")\n");
		sb.append("  AND lang.lkey = '").append(lang).append("'\n");
		sb.append("  AND slang.lkey = '").append(lang).append("'\n");
		
		sb.append("SELECT code from status WHERE type='basinSrfM'");
		return sb.toString();
	}
}