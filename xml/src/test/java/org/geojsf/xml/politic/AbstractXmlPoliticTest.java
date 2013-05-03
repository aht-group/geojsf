package org.geojsf.xml.politic;

import java.io.File;


import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlPoliticTest extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlPoliticTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/politic";
	protected static File fXml;
}