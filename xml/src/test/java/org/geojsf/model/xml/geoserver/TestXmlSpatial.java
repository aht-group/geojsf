package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSpatial extends AbstractXmlGeoserverTest<Spatial>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSpatial.class);
	
	public TestXmlSpatial() {super(Spatial.class);}
	public static Spatial create(boolean withChildren){return (new TestXmlSpatial()).build(withChildren);}
 
    
    public Spatial build(boolean withChilds)
    {
    	Spatial xml = new Spatial();
    	xml.setCreateIndex(true);
    	xml.setEnableIndex(true);
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlSpatial test = new TestXmlSpatial();
  		test.saveReferenceXml();
	}
}