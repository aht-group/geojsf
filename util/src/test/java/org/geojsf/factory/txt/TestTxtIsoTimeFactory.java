package org.geojsf.factory.txt;

import java.util.Date;

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
	
	@Ignore @Test
	public void value()
	{
		String expected = "2013....";
		String actual = TxtIsoTimeFactory.toDate(d1);
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