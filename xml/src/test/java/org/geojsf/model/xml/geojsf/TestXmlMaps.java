package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMaps extends AbstractXmlGeojsfTest<Maps>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMaps.class);
	
	public TestXmlMaps() {super(Maps.class);}
	public static Maps create(boolean withChildren) {return (new TestXmlMaps()).build(withChildren);}

    
    public Maps build(boolean withChilds)
    {
    	Maps xml = new Maps();

    	if(withChilds)
    	{
    		xml.getMap().add(TestXmlMap.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlMaps test = new TestXmlMaps();
		test.saveReferenceXml();
    }
}