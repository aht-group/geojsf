package org.geojsf.model.xml.geoserver;

import java.io.File;


import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGeoserverTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGeoserverTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/geoserver";
	protected static File fXml;
}