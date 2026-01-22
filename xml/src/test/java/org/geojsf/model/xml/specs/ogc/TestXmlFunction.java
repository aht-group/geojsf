package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFunction extends AbstractXmlOgcTest<Function>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFunction.class);
	
	public TestXmlFunction(){super(Function.class);}
	public static Function create(boolean withChildren){return (new TestXmlFunction()).build(withChildren);}
    
    public Function build(boolean withChilds)
    {
    	Function xml = new Function();
    	xml.setName("env");

    	if(withChilds)
    	{
    		xml.getLiteral().add(TestXmlLiteral.create(false));
    		xml.getLiteral().add(TestXmlLiteral.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlFunction test = new TestXmlFunction();
		test.saveReferenceXml();
    }
}