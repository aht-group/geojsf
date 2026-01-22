package org.geojsf.model.xml.government;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDistrict extends AbstractXmlGovernmentTest<District>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDistrict.class);
	
	public TestXmlDistrict(){super(District.class);}
	public static District create(boolean withChildren){return (new TestXmlDistrict()).build(withChildren);}
    
    public District build(boolean withChilds)
    {
    	District xml = new District();
    	xml.setId(123l);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
//    		xml.setRegion(TestXmlRegion.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlDistrict test = new TestXmlDistrict();
		test.saveReferenceXml();
    }
}