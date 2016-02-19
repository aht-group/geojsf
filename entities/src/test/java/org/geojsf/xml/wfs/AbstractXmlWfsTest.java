package org.geojsf.xml.wfs;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlWfsTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlWfsTest.class);	
	
	public AbstractXmlWfsTest(Class<T> cXml)
	{
		super(cXml,"specs/wfs");
	}
}