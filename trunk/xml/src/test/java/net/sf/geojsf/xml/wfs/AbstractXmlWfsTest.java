package net.sf.geojsf.xml.wfs;

import java.io.File;

import net.sf.geojsf.test.AbstractGeoJsfXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlWfsTest extends AbstractGeoJsfXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlWfsTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/wfs";
	protected static File fXml;
}