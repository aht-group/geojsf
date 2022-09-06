package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlOgcTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOgcTest.class);	
	
	public AbstractXmlOgcTest(Class<T> cXml)
	{
		super(cXml,"specs/ogc");
	}
}