package org.geojsf.model.xml.monitoring;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStations extends AbstractXmlMonitoringTest<Stations>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStations.class);
	
	public TestXmlStations(){super(Stations.class);}
	public static Stations create(boolean withChildren){return (new TestXmlStations()).build(withChildren);}
    
    public Stations build(boolean withChilds)
    {
    	Stations xml = new Stations();
    	
    	if(withChilds)
    	{
    		xml.getStation().add(TestXmlStation.create(false));
    		xml.getStation().add(TestXmlStation.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();	
		TestXmlStations test = new TestXmlStations();
		test.saveReferenceXml();
    }
}