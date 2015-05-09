package org.geojsf.xml.ogc;

import java.io.File;


import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlOgcTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOgcTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/ogc";
	protected static File fXml;
}