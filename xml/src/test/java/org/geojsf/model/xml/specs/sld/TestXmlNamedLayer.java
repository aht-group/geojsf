package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlNamedLayer extends AbstractXmlSldTest<NamedLayer>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlNamedLayer.class);
	
	public TestXmlNamedLayer(){super(NamedLayer.class);}
	public static NamedLayer create(boolean withChildren){return (new TestXmlNamedLayer()).build(withChildren);}
    
    public NamedLayer build(boolean withChilds)
    {
    	NamedLayer xml = new NamedLayer();
    	
    	if(withChilds)
    	{
    		xml.setName(TestXmlName.create(false));
    		xml.setUserStyle(TestXmlUserStyle.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlNamedLayer test = new TestXmlNamedLayer();
		test.saveReferenceXml();
    }
}