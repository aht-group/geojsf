package org.geojsf.model.xml.specs.gml;

import java.nio.file.Paths;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGmlTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGmlTest.class);	
	
	public AbstractXmlGmlTest(Class<T> cXml)
	{
		super(cXml,Paths.get("specs","gml"));
	}
}