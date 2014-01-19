package org.geojsf.factory.txt;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtIsoTimeFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtIsoTimeFactory.class);
		
	private Date d1,d2;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		//Initialize dates with fixed values
	}
	
	@Test
	public void value()
	{
		String expected = "2011-08-20T12:00:00:000Z";
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(Calendar.DAY_OF_MONTH, 20);
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.YEAR, 2011);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		TimeZone tz = calendar.getTimeZone();
		
		d1 = calendar.getTime();
		String actual = TxtIsoTimeFactory.toDate(d1);
		
		logger.info("Time GMT string  : " +d1.toGMTString());
		logger.info("Timezone         : " +tz.getDisplayName());
		logger.info("ISO result       : " +actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Ignore @Test
	public void range()
	{
		String expected = "2013..../2014...";
		String actual = TxtIsoTimeFactory.toRange(d1, d2);
		Assert.assertEquals(expected, actual);
	}
}