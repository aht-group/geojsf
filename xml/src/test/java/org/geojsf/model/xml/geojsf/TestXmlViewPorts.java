package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViewPorts extends AbstractXmlGeojsfTest<ViewPorts>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPorts.class);
	
	public TestXmlViewPorts(){super(ViewPorts.class);}
	public static ViewPorts create(boolean withChildren){return (new TestXmlViewPorts()).build(withChildren);}

    
    public ViewPorts build(boolean withChilds)
    {
    	ViewPorts xml = new ViewPorts();
     	
    	if(withChilds)
    	{
    		xml.getViewPort().add(TestXmlViewPort.create(false));
    		xml.getViewPort().add(TestXmlViewPort.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlViewPorts test = new TestXmlViewPorts();
		test.saveReferenceXml();
    }
}