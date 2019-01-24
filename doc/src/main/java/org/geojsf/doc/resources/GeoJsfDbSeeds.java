package org.geojsf.doc.resources;

import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.factory.xml.system.io.db.XmlSeedFactory;
import org.jeesl.model.xml.system.io.db.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfDbSeeds
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfDbSeeds.class);
	
	public static void apply(Db db)
	{
		db.getSeed().add(XmlSeedFactory.build(GeoJsfSldType.class.getName(),"geojsf/db/sldType.xml"));
	}
}