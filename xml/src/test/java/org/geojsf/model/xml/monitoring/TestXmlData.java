package org.geojsf.model.xml.monitoring;

import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;

public class TestXmlData extends AbstractXmlMonitoringTest<Data>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	public TestXmlData(){super(Data.class);}
	public static Data create(boolean withChildren){return (new TestXmlData()).build(withChildren);}
    
    public Data build(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123);
    	xml.setRange(345);
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	
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
		GeoJsfXmlTstBootstrap.init();
		TestXmlData test = new TestXmlData();
		test.saveReferenceXml();
    }
}