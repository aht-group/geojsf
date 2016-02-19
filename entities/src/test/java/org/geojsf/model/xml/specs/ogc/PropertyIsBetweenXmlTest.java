package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyIsBetweenXmlTest extends AbstractXmlOgcTest<PropertyIsBetween>
{
	final static Logger logger = LoggerFactory.getLogger(PropertyIsBetweenXmlTest.class);
	
	public PropertyIsBetweenXmlTest(){super(PropertyIsBetween.class);}
	public static PropertyIsBetween create(boolean withChildren){return (new PropertyIsBetweenXmlTest()).build(withChildren);}
    
    public PropertyIsBetween build(boolean withChilds)
    {
    	PropertyIsBetween xml = new PropertyIsBetween();

    	if(withChilds)
    	{
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		PropertyIsBetweenXmlTest test = new PropertyIsBetweenXmlTest();
		test.saveReferenceXml();
    }
}