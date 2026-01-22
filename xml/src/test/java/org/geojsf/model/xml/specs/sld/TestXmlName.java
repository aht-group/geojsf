package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlName extends AbstractXmlSldTest<Name>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlName.class);
	
	public TestXmlName(){super(Name.class);}
	public static Name create(boolean withChildren){return (new TestXmlName()).build(withChildren);}
    
    public Name build(boolean withChilds)
    {
    	Name xml = new Name();
    	xml.setValue("myName");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlName test = new TestXmlName();
		test.saveReferenceXml();
    }
}