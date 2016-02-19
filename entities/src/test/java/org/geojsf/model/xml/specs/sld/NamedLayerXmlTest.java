package org.geojsf.model.xml.specs.sld;

import org.geojsf.model.xml.specs.se.NameXmlTest;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamedLayerXmlTest extends AbstractXmlSldTest<NamedLayer>
{
	final static Logger logger = LoggerFactory.getLogger(NamedLayerXmlTest.class);
	
	public NamedLayerXmlTest(){super(NamedLayer.class);}
	public static NamedLayer create(boolean withChildren){return (new NamedLayerXmlTest()).build(withChildren);}
    
    public NamedLayer build(boolean withChilds)
    {
    	NamedLayer xml = new NamedLayer();
    	
    	if(withChilds)
    	{
    		xml.setName(NameXmlTest.create(false));
    		xml.setUserStyle(UserStyleXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		NamedLayerXmlTest test = new NamedLayerXmlTest();
		test.saveReferenceXml();
    }
}