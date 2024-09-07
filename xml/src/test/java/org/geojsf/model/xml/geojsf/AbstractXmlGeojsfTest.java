package org.geojsf.model.xml.geojsf;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlGeojsfTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlGeojsfTest.class);	
	
	public AbstractXmlGeojsfTest(Class<T> cXml)
	{
   		super(cXml,"geojsf");
	}
}