package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTitle extends AbstractXmlSldTest<Title>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTitle.class);
	
	public TestXmlTitle(){super(Title.class);}
	public static Title create(boolean withChildren){return (new TestXmlTitle()).build(withChildren);}
    
    public Title build(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setValue("myTitle");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlTitle test = new TestXmlTitle();
		test.saveReferenceXml();
    }
}