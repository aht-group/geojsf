package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPropertyIsEqualTo extends AbstractXmlOgcTest<PropertyIsEqualTo>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPropertyIsEqualTo.class);
	
	public TestXmlPropertyIsEqualTo(){super(PropertyIsEqualTo.class);}
	public static PropertyIsEqualTo create(boolean withChildren){return (new TestXmlPropertyIsEqualTo()).build(withChildren);}
    
    public PropertyIsEqualTo build(boolean withChilds)
    {
    	PropertyIsEqualTo xml = new PropertyIsEqualTo();

    	if(withChilds)
    	{
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    		xml.setLiteral(TestXmlLiteral.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlPropertyIsEqualTo test = new TestXmlPropertyIsEqualTo();
		test.saveReferenceXml();
    }
}