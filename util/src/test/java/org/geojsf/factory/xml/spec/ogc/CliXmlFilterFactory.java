package org.geojsf.factory.xml.spec.ogc;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.model.xml.specs.ogc.Filter;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class CliXmlFilterFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliXmlFilterFactory.class);
	
	private void interval()
	{
		Filter filter = XmlFilterFactory.interval("value", "lb1", 1, "ub1", 2);
		JaxbUtil.info(filter);
	}
	
	public static void main(String[] args)
	{
		GeoJsfUtilsTestBootstrap.init();
		CliXmlFilterFactory cli = new CliXmlFilterFactory();
		cli.interval();
	}
}