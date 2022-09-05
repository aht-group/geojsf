package org.geojsf.factory.txt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;

public class TestTxtIsoTimeFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtIsoTimeFactory.class);
	
	private ZonedDateTime zdt1,zdt2;
	
	@Before
	public void init()
	{
		zdt1 = ZonedDateTime.of(LocalDateTime.of(2011,7,20,12,0,0),ZoneId.of("UTC"));
		zdt2 = zdt1.plusDays(5);
	}
	
	@Test
	public void value()
	{
		String expected = "2011-07-20T12:00:00Z";
		String actual = TxtIsoTimeFactory.toDate(DateUtil.toDate(zdt1));
		
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void range()
	{
		String expected = "2011-07-20T12:00:00Z/2011-07-25T12:00:00Z";
		String actual = TxtIsoTimeFactory.toRange(DateUtil.toDate(zdt1),DateUtil.toDate(zdt2));
	
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void list()
	{
		String expected = "2011-07-20T12:00:00Z,2011-07-25T12:00:00Z";
		ArrayList<Date> list = new ArrayList<Date>();
		list.add(DateUtil.toDate(zdt1));
		list.add(DateUtil.toDate(zdt2));
		String actual = TxtIsoTimeFactory.toList(list);
	
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
}