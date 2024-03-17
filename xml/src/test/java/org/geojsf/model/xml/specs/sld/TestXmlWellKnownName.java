package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWellKnownName extends AbstractXmlSldTest<WellKnownName>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWellKnownName.class);
	
	public TestXmlWellKnownName(){super(WellKnownName.class);}
	public static WellKnownName create(boolean withChildren){return (new TestXmlWellKnownName()).build(withChildren);}
    
    public WellKnownName build(boolean withChilds)
    {
    	WellKnownName xml = new WellKnownName();
    	xml.setValue("myWellKnownName");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlWellKnownName test = new TestXmlWellKnownName();
		test.saveReferenceXml();
    }
}