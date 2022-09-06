package org.geojsf.model.xml.specs.gml;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPoint extends AbstractXmlGmlTest<Point>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPoint.class);
	
	public TestXmlPoint(){super(Point.class);}
	public static Point create(boolean withChildren){return (new TestXmlPoint()).build(withChildren);}
    
    public Point build(boolean withChilds)
    {
    	Point xml = new Point();
    	xml.setSrsName("mySrsName");
    	
    	if(withChilds)
    	{
    		xml.setCoordinates(TestXmlCoordinates.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();	
		TestXmlPoint test = new TestXmlPoint();
		test.saveReferenceXml();
    }
}