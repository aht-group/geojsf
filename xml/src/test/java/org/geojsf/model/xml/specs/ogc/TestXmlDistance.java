package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDistance extends AbstractXmlOgcTest<Distance>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDistance.class);
	
	public TestXmlDistance(){super(Distance.class);}
	public static Distance create(boolean withChildren){return (new TestXmlDistance()).build(withChildren);}
    
    public Distance build(boolean withChilds)
    {
    	Distance xml = new Distance();
    	xml.setUnits("myUnits");
    	xml.setValue("myValue");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();	
		TestXmlDistance test = new TestXmlDistance();
		test.saveReferenceXml();
    }
}