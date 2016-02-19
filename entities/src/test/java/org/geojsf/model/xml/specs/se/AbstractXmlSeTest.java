package org.geojsf.model.xml.specs.se;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSeTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSeTest.class);	
	
	public AbstractXmlSeTest(Class<T> cXml)
	{
		super(cXml,"specs/se");
	}
}