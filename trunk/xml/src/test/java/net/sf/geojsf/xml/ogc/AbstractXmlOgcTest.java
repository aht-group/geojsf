package net.sf.geojsf.xml.ogc;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlOgcTest extends AbstractGeoJsfXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlOgcTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/ogc";
	protected static File fXml;
}