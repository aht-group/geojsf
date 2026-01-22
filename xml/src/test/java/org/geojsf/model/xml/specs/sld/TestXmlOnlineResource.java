package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOnlineResource extends AbstractXmlSldTest<OnlineResource>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOnlineResource.class);
	
	public TestXmlOnlineResource(){super(OnlineResource.class);}
	public static OnlineResource create(boolean withChildren){return (new TestXmlOnlineResource()).build(withChildren);}
    
    public OnlineResource build(boolean withChilds)
    {
    	OnlineResource xml = new OnlineResource();
    	xml.setType("simple");
    	xml.setHref("myHref");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlOnlineResource test = new TestXmlOnlineResource();
		test.saveReferenceXml();
    }
}