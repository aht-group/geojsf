package org.geojsf.model.ejb.io.db.flyway;

import java.util.ArrayList;
import java.util.List;

import org.geojsf.model.ejb.sld.GeoSld;
import org.geojsf.model.ejb.sld.GeoSldRule;
import org.geojsf.model.ejb.sld.GeoSldStyle;
import org.geojsf.model.ejb.sld.GeoSldTemplate;
import org.geojsf.model.ejb.sld.GeoSldType;
import org.jeesl.interfaces.controller.io.db.JeesDdlClassProvider;
import org.jeesl.interfaces.controller.io.db.flyway.JeeslFlywayPathProvider;
import org.jeesl.model.ejb.io.db.flyway.FlywayIoLocalePathProvider;

public class FlywayGeoSldPathProvider implements JeeslFlywayPathProvider, JeesDdlClassProvider
{	
	public static FlywayGeoSldPathProvider instance() {return new FlywayGeoSldPathProvider();}
	
	@Override public String getRootDirectory() {return "geojsf/system/io/db/migration/sld";}
	
	@Override public String getBaselineTables() {return this.getRootDirectory()+"/"+JeeslFlywayPathProvider.sqlTables;}
	@Override public String getBaselineConstraints() {return this.getRootDirectory()+"/"+JeeslFlywayPathProvider.sqlConstraints;}
	
	@Override public List<Class<?>> getMdsClasses()
	{
		List<Class<?>> list = new ArrayList<>();
		list.addAll(FlywayIoLocalePathProvider.instance().getMdsClasses());
		list.add(GeoSldStyle.class);
		list.add(GeoSldType.class);
		list.add(GeoSldTemplate.class);
		list.add(GeoSld.class);
		list.add(GeoSldRule.class);
		return list;
	}
}