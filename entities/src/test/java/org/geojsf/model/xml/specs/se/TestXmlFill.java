package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFill extends AbstractXmlSeTest<Fill>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFill.class);
	
	public TestXmlFill(){super(Fill.class);}
	public static Fill create(boolean withChildren){return (new TestXmlFill()).build(withChildren);}
    
    public Fill build(boolean withChilds)
    {
    	Fill xml = new Fill();
    	
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
		TestXmlFill test = new TestXmlFill();
		test.saveReferenceXml();
    }
}