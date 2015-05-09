package org.geojsf.xml.gml;

import java.io.File;


import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGmlTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGmlTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/gml";
	protected static File fXml;
}