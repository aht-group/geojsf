package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFormat extends AbstractXmlSldTest<Format>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFormat.class);
	
	public TestXmlFormat(){super(Format.class);}
	public static Format create(boolean withChildren){return (new TestXmlFormat()).build(withChildren);}
    
    public Format build(boolean withChilds)
    {
    	Format xml = new Format();
    	xml.setValue("myFormat");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlFormat test = new TestXmlFormat();
		test.saveReferenceXml();
    }
}