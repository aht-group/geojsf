package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFeatureTypes extends AbstractXmlGeoserverTest<FeatureTypes>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFeatureTypes.class);
	
	public TestXmlFeatureTypes() {super(FeatureTypes.class);}
	public static FeatureTypes create(boolean withChildren){return (new TestXmlFeatureTypes()).build(withChildren);}
 
    
    public FeatureTypes build(boolean withChilds)
    {
    	FeatureTypes xml = new FeatureTypes();
    	
    	if(withChilds)
    	{
    		xml.getFeatureType().add(TestXmlFeatureType.create(false));
    		xml.getFeatureType().add(TestXmlFeatureType.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlFeatureTypes test = new TestXmlFeatureTypes();
  		test.saveReferenceXml();
	}
}