package org.geojsf.model.xml.monitoring;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlMonitoringTest <T extends Object> extends AbstractGeoJsfXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMonitoringTest.class);

    public AbstractXmlMonitoringTest(Class<T> cXml)
	{
		super(cXml,"monitoring");
	}
}