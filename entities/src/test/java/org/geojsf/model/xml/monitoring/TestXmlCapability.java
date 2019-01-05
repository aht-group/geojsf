package org.geojsf.model.xml.monitoring;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCapability extends AbstractXmlMonitoringTest<Capability>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCapability.class);
	
	public TestXmlCapability(){super(Capability.class);}
	public static Capability create(boolean withChildren){return (new TestXmlCapability()).build(withChildren);}
    
    public Capability build(boolean withChilds)
    {
    	Capability xml = new Capability();
    	xml.setId(123);
    	
    	if(withChilds)
    	{
    		xml.setType(XmlTypeFactory.create("myType"));
    		xml.setStatus(XmlStatusFactory.create("myStatus"));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlCapability test = new TestXmlCapability();
		test.saveReferenceXml();
    }
}