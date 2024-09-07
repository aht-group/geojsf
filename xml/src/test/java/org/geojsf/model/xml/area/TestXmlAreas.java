package org.geojsf.model.xml.area;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAreas extends AbstractXmAreaTest<Areas>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAreas.class);
	
	public TestXmlAreas() {super(Areas.class);}
 
    @Override public Areas build(boolean withChilds)
    {
    	Areas xml = new Areas();
    	
    	if(withChilds)
    	{
    		xml.getBasin().add(TestXmlBasin.instance().build(false));
    		xml.getBasin().add(TestXmlBasin.instance().build(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlAreas test = new TestXmlAreas();
		test.saveReferenceXml();
    }
}