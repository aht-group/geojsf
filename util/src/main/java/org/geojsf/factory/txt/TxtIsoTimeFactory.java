package org.geojsf.factory.txt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIsoTimeFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtIsoTimeFactory.class);
	
	
	
	public static String toDate(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(date);
	}
	
	public static String toRange(Date dateFrom, Date dateTo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(toDate(dateFrom));
		sb.append("/");
		sb.append(toDate(dateTo));
		return sb.toString();
	}
}