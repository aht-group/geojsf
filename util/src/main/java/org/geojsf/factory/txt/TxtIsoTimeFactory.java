package org.geojsf.factory.txt;

import java.text.DecimalFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIsoTimeFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtIsoTimeFactory.class);
	
	
	
	public static String toDate(Date date)
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
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