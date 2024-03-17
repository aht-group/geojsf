package org.geojsf.model.xml.monitoring;

import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStation extends AbstractXmlMonitoringTest<Station>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStation.class);
	
	public TestXmlStation(){super(Station.class);}
	public static Station create(boolean withChildren){return (new TestXmlStation()).build(withChildren);}
    
    public Station build(boolean withChilds)
    {
    	Station xml = new Station();
    	xml.setId(123l);
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.getCapability().add(TestXmlCapability.create(false));xml.getCapability().add(TestXmlCapability.create(false));
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setWkt(TestXmlWkt.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlStation test = new TestXmlStation();
		test.saveReferenceXml();
    }
}