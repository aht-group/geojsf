package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUpperBoundary extends AbstractXmlOgcTest<UpperBoundary>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUpperBoundary.class);
	
	public TestXmlUpperBoundary(){super(UpperBoundary.class);}
	public static UpperBoundary create(boolean withChildren){return (new TestXmlUpperBoundary()).build(withChildren);}
    
    public UpperBoundary build(boolean withChilds)
    {
    	UpperBoundary xml = new UpperBoundary();
    	if(withChilds)
    	{
    		xml.setFunction(TestXmlFunction.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlUpperBoundary test = new TestXmlUpperBoundary();
		test.saveReferenceXml();
    }
}