package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverage extends AbstractXmlGeoserverTest<Coverage>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverage.class);
	
	public TestXmlCoverage(){super(Coverage.class);}
	public static Coverage create(boolean withChildren){return (new TestXmlCoverage()).build(withChildren);}
 
    public Coverage build(boolean withChilds)
    {
    	Coverage xml = new Coverage();
    	xml.setName("myName");
    	
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
	
 	public static void main(String[] args)
     {
 		GeoJsfBootstrap.wrap();	
 		TestXmlCoverage test = new TestXmlCoverage();
 		test.saveReferenceXml();
     }
}