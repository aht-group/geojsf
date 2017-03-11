package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStroke extends AbstractXmlSldTest<Stroke>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStroke.class);
	
	public TestXmlStroke(){super(Stroke.class);}
	public static Stroke create(boolean withChildren){return (new TestXmlStroke()).build(withChildren);}
    
    public Stroke build(boolean withChilds)
    {
    	Stroke xml = new Stroke();
    	
    	if(withChilds)
    	{
    		xml.getSvgParameter().add(TestXmlSvgParameter.create(false));
    		xml.getSvgParameter().add(TestXmlSvgParameter.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlStroke test = new TestXmlStroke();
		test.saveReferenceXml();
    }
}