package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlShapeDir extends AbstractXmlGeoserverTest<ShapeDir>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlShapeDir.class);
	
	public TestXmlShapeDir() {super(ShapeDir.class);}
	public static ShapeDir create(boolean withChildren){return (new TestXmlShapeDir()).build(withChildren);}
 
    
    public ShapeDir build(boolean withChilds)
    {
    	ShapeDir xml = new ShapeDir();
    	xml.setUrl("myUrl");
    	xml.setCharset("myCharSet");
    	xml.setMemoryBuffer(true);
    	xml.setCacheMemoryMaps(true);
    	
    	if(withChilds)
    	{
    		xml.setSpatial(TestXmlSpatial.create(true));
    	}
    	
    	return xml;
    }
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.wrap();	
  		TestXmlShapeDir test = new TestXmlShapeDir();
  		test.saveReferenceXml();
	}
}