package org.geojsf.model.xml.geoserver;

import java.nio.file.Paths;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGeoserverTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGeoserverTest.class);	
	
	public AbstractXmlGeoserverTest(Class<T> cXml)
	{
   		super(cXml,Paths.get("geoserver"));
	}
}