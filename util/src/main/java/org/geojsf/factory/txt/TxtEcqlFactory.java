package org.geojsf.factory.txt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtEcqlFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtEcqlFactory.class);
		
	public static String utcDateTime(LocalDate ld)
	{
		return ld.atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME);
	}
}	