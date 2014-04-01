package org.geojsf.factory.txt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIsoTimeFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtIsoTimeFactory.class);
	
	
	
	public static String toDate(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(date);
	}
	
	public static String toList(ArrayList<Date> timeList)
	{
		StringBuffer sb = new StringBuffer();
		for (Date date : timeList)
		{
			sb.append(toDate(date));
			sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
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