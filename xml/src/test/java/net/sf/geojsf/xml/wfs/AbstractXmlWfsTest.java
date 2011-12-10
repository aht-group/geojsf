package net.sf.geojsf.xml.wfs;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlWfsTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlWfsTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/wfs";
	protected static File fXml;
}