package net.sf.geojsf.xml.openlayers;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlOpenlayersTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOpenlayersTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/openlayers";
	protected static File fXml;
}