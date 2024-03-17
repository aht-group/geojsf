package org.geojsf.model.xml.specs.sld;

import org.geojsf.model.xml.specs.ogc.TestXmlFunction;
import org.geojsf.model.xml.specs.ogc.TestXmlLiteral;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class TestXmlSvgParameter extends AbstractXmlSldTest<SvgParameter>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSvgParameter.class);
	
	public TestXmlSvgParameter(){super(SvgParameter.class);}
	public static SvgParameter create(boolean withChildren){return (new TestXmlSvgParameter()).build(withChildren);}
    
    public SvgParameter build(boolean withChilds)
    {
    	SvgParameter xml = new SvgParameter();
    	xml.setName("myName");
    	if(withChilds)
    	{
    		xml.getContent().add("#");
    		xml.getContent().add(TestXmlFunction.create(false));
    		xml.getContent().add(TestXmlLiteral.create(false));
    		xml.getContent().add(TestXmlLiteral.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlSvgParameter test = new TestXmlSvgParameter();
		test.saveReferenceXml();
    }
}