package net.sf.geojsf.xml.gml;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlGmlTest extends AbstractGeoJsfXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlGmlTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/gml";
	protected static File fXml;
}