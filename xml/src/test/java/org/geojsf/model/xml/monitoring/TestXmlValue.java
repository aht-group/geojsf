package org.geojsf.model.xml.monitoring;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlValue extends AbstractXmlMonitoringTest<Value>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlValue.class);
	
	public TestXmlValue(){super(Value.class);}
	public static Value create(boolean withChildren){return (new TestXmlValue()).build(withChildren);}
    
    public Value build(boolean withChilds)
    {
    	Value xml = new Value();
    	xml.setType("myT");
    	xml.setValue("123.3");
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlValue test = new TestXmlValue();
		test.saveReferenceXml();
    }
}