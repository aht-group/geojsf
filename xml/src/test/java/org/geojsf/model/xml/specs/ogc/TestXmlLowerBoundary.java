package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLowerBoundary extends AbstractXmlOgcTest<LowerBoundary>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLowerBoundary.class);
	
	public TestXmlLowerBoundary(){super(LowerBoundary.class);}
	public static LowerBoundary create(boolean withChildren){return (new TestXmlLowerBoundary()).build(withChildren);}
    
    public LowerBoundary build(boolean withChilds)
    {
    	LowerBoundary xml = new LowerBoundary();
    	if(withChilds)
    	{
    		xml.setFunction(TestXmlFunction.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlLowerBoundary test = new TestXmlLowerBoundary();
		test.saveReferenceXml();
    }
}