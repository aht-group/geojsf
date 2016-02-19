package org.geojsf.model.xml.specs.gml;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoordinates extends AbstractXmlGmlTest<Coordinates>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoordinates.class);
	
	public TestXmlCoordinates(){super(Coordinates.class);}
	public static Coordinates create(boolean withChildren){return (new TestXmlCoordinates()).build(withChildren);}
    
    public Coordinates build(boolean withChilds)
    {
    	Coordinates xml = new Coordinates();
    	xml.setValue("x,y");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlCoordinates test = new TestXmlCoordinates();
		test.saveReferenceXml();
    }
}