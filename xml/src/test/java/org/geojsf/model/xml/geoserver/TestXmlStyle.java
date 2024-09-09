package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStyle extends AbstractXmlGeoserverTest<Style>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyle.class);
	
	public TestXmlStyle() {super(Style.class);}
	public static Style create(boolean withChildren){return (new TestXmlStyle()).build(withChildren);}
 
    
    public Style build(boolean withChilds)
    {
    	Style xml = new Style();
    	xml.setName("myName");
    		
    	if(withChilds)
    	{
    		xml.setWorkspace(TestXmlWorkspace.create(false));
    	}
    	
    	return xml;
    }
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlStyle test = new TestXmlStyle();
  		test.saveReferenceXml();
	}
}