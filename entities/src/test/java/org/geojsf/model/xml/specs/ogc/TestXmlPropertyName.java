package org.geojsf.model.xml.specs.ogc;

import org.geojsf.model.xml.ogc.PropertyName;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPropertyName extends AbstractXmlOgcTest<PropertyName>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPropertyName.class);
	
	public TestXmlPropertyName(){super(PropertyName.class);}
	public static PropertyName create(boolean withChildren){return (new TestXmlPropertyName()).build(withChildren);}
    
    public PropertyName build(boolean withChilds)
    {
    	PropertyName xml = new PropertyName();
    	xml.setValue("myValue");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlPropertyName test = new TestXmlPropertyName();
		test.saveReferenceXml();
    }
}