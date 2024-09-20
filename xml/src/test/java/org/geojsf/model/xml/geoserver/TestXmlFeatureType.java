package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFeatureType extends AbstractXmlGeoserverTest<FeatureType>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFeatureType.class);
	
	public TestXmlFeatureType() {super(FeatureType.class);}
	public static FeatureType create(boolean withChildren){return (new TestXmlFeatureType()).build(withChildren);}
 
    
    public FeatureType build(boolean withChilds)
    {
    	FeatureType xml = new FeatureType();
    	xml.setName("myName");
    	xml.setNativeName("myNativeName");
    	
    		
    	if(withChilds)
    	{
    		xml.setDataStore(TestXmlDataStore.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlFeatureType test = new TestXmlFeatureType();
  		test.saveReferenceXml();
	}
}