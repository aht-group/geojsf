package org.geojsf.factory.wkt;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SridFactory
{
	final static Logger logger = LoggerFactory.getLogger(SridFactory.class);

	public static int toSrid(CoordinateReferenceSystem crs)
	{
		String code = crs.getCoordinateSystem().getName().getCode();
				
//		logger.info("Name: "+name);
//		logger.info("Code: "+code);
		
		if(code.equals("GCS_WGS_1984")) {return 4326;}
		else
		{
			logger.warn("No SRID defined for "+crs.getCoordinateSystem().getName().toString());
		}
		
		return 0;
	}
}