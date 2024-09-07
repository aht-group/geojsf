package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViewPort extends AbstractXmlGeojsfTest<ViewPort>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPort.class);
	
	public TestXmlViewPort() {super(ViewPort.class);}
	public static ViewPort create(boolean withChildren) {return (new TestXmlViewPort()).build(withChildren);}
    
    public ViewPort build(boolean withChilds)
    {
    	ViewPort xml = new ViewPort();
    	xml.setId(123l);
    	xml.setLat(1.23);
    	xml.setLon(4.56);
    	xml.setTop(2.22);
    	xml.setLeft(3.33);
    	xml.setRight(4.44);
    	xml.setBottom(5.55);
     	
    	if(withChilds)
    	{
    		xml.setScale(TestXmlScale.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlViewPort test = new TestXmlViewPort();
		test.saveReferenceXml();
    }
}