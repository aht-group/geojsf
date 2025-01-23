package org.geojsf.model.xml.government;

import java.nio.file.Paths;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGovernmentTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGovernmentTest.class);

    public AbstractXmlGovernmentTest(Class<T> cXml)
   	{
   		super(cXml,Paths.get("location"));
   	}
}