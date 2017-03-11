package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSldTest<T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSldTest.class);	
	
	public AbstractXmlSldTest(Class<T> cXml)
	{
		super(cXml,"specs/sld");
	}
}