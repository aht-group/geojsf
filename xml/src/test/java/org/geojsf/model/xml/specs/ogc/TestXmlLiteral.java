package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLiteral extends AbstractXmlOgcTest<Literal>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLiteral.class);
	
	public TestXmlLiteral(){super(Literal.class);}
	public static Literal create(boolean withChildren){return (new TestXmlLiteral()).build(withChildren);}
    
    public Literal build(boolean withChilds)
    {
    	Literal xml = new Literal();
    	xml.setValue("myLiteral");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlLiteral test = new TestXmlLiteral();
		test.saveReferenceXml();
    }
}