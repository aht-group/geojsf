package org.geojsf.factory.txt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtIsoTimeFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtIsoTimeFactory.class);
		
	private Date d1,d2;
	
	@Before
	public void init()
	{
		//Initialize dates with fixed values
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(Calendar.DAY_OF_MONTH, 20);
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.YEAR, 2011);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		d1 = calendar.getTime();
		d2 = new DateTime(d1).plusDays(5).toDate();
	}
	
	@Test
	public void value()
	{
		String expected = "2011-08-20T12:00:00Z";
		String actual = TxtIsoTimeFactory.toDate(d1);
		
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void range()
	{
		String expected = "2011-08-20T12:00:00Z/2011-08-25T12:00:00Z";
		String actual = TxtIsoTimeFactory.toRange(d1, d2);
	
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void list()
	{
		String expected = "2011-08-20T12:00:00Z,2011-08-25T12:00:00Z";
		ArrayList<Date> list = new ArrayList<Date>();
		list.add(d1);
		list.add(d2);
		String actual = TxtIsoTimeFactory.toList(list);
	
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
}