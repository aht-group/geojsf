package org.geojsf.model.xml.area;

import java.nio.file.Paths;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmAreaTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmAreaTest.class);

    public AbstractXmAreaTest(Class<T> cXml)
   	{
   		super(cXml,Paths.get("area"));
   	}
}