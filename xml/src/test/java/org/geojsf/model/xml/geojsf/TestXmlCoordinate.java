package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoordinate extends AbstractXmlGeojsfTest<Coordinate>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoordinate.class);
	
	public TestXmlCoordinate() {super(Coordinate.class);}
	public static Coordinate create(boolean withChildren){return (new TestXmlCoordinate()).build(withChildren);}

    
    public Coordinate build(boolean withChilds)
    {
    	Coordinate xml = new Coordinate();
    	xml.setLat(1.234);
    	xml.setLon(5.678);
    	
    	if(withChilds)
    	{
    	
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlCoordinate test = new TestXmlCoordinate();
		test.saveReferenceXml();
    }
}