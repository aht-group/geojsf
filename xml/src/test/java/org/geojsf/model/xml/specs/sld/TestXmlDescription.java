package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescription extends AbstractXmlSldTest<Description>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescription.class);
	
	public TestXmlDescription(){super(Description.class);}
	public static Description create(boolean withChildren){return (new TestXmlDescription()).build(withChildren);}
    
    public Description build(boolean withChilds)
    {
    	Description xml = new Description();
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlDescription test = new TestXmlDescription();
		test.saveReferenceXml();
    }
}