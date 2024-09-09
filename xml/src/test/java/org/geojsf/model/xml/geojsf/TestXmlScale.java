package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScale extends AbstractXmlGeojsfTest<Scale>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScale.class);
	
	public TestXmlScale() {super(Scale.class);}
	public static Scale create(boolean withChildren){return (new TestXmlScale()).build(withChildren);}

    
    public Scale build(boolean withChilds)
    {
    	Scale xml = new Scale();
    	xml.setUnit("m");
    	xml.setValue(10000);
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlScale test = new TestXmlScale();
		test.saveReferenceXml();
    }
}