package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPropertyIsBetween extends AbstractXmlOgcTest<PropertyIsBetween>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPropertyIsBetween.class);
	
	public TestXmlPropertyIsBetween(){super(PropertyIsBetween.class);}
	public static PropertyIsBetween create(boolean withChildren){return (new TestXmlPropertyIsBetween()).build(withChildren);}
    
    public PropertyIsBetween build(boolean withChilds)
    {
    	PropertyIsBetween xml = new PropertyIsBetween();

    	if(withChilds)
    	{
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    		xml.setLowerBoundary(TestXmlLowerBoundary.create(false));
    		xml.setUpperBoundary(TestXmlUpperBoundary.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlPropertyIsBetween test = new TestXmlPropertyIsBetween();
		test.saveReferenceXml();
    }
}