package org.geojsf.model.xml.monitoring;

import org.exlp.test.AbstractXmlTest;
import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlData extends AbstractXmlMonitoringTest<Data>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	public TestXmlData(){super(Data.class);}
	public static Data create(boolean withChildren){return (new TestXmlData()).build(withChildren);}
    
    public Data build(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123l);
    	xml.setRange(345l);
    	xml.setRecord(AbstractXmlTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.getValue().add(TestXmlValue.create(false));
    		xml.getValue().add(TestXmlValue.create(false));
    		xml.setWkt(TestXmlWkt.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlData test = new TestXmlData();
		test.saveReferenceXml();
    }
}