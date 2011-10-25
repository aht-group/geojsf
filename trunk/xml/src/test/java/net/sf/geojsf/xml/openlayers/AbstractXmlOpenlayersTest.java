package net.sf.geojsf.xml.openlayers;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlOpenlayersTest extends AbstractGeoJsfXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlOpenlayersTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/openlayers";
	protected static File fXml;
}