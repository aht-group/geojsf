package org.geojsf.model.xml.specs.sld;

import org.geojsf.model.xml.specs.ogc.TestXmlFunction;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class TestXmlSize extends AbstractXmlSldTest<Size>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSize.class);
	
	public TestXmlSize(){super(Size.class);}
	public static Size create(boolean withChildren){return (new TestXmlSize()).build(withChildren);}
    
    public Size build(boolean withChilds)
    {
    	Size xml = new Size();
    	
    	if(withChilds)
    	{
    		xml.getContent().add(TestXmlFunction.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlSize test = new TestXmlSize();
		test.saveReferenceXml();
    }
}