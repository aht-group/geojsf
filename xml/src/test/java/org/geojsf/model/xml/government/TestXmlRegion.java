package org.geojsf.model.xml.government;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRegion extends AbstractXmlGovernmentTest<Region>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRegion.class);
	
	public TestXmlRegion(){super(Region.class);}
	public static Region create(boolean withChildren){return (new TestXmlRegion()).build(withChildren);}
    
    public Region build(boolean withChilds)
    {
    	Region xml = new Region();
    	xml.setId(123l);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
//    		xml.getDistrict().add(TestXmlDistrict.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlRegion test = new TestXmlRegion();
		test.saveReferenceXml();
    }
}