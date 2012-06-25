package net.sf.geojsf.xml.geojsf;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGmlTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGmlTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/geojsf";
	protected static File fXml;
}