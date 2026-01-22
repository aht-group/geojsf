package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.factory.xml.io.locale.status.XmlTypeFactory;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSldTemplate extends AbstractXmlGeojsfTest<SldTemplate>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSldTemplate.class);
	
	public TestXmlSldTemplate() {super(SldTemplate.class);}
	public static SldTemplate create(boolean withChildren){return (new TestXmlSldTemplate()).build(withChildren);}

    
    public SldTemplate build(boolean withChilds)
    {
    	SldTemplate xml = new SldTemplate();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	    	
    	if(withChilds)
    	{
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    		xml.setType(XmlTypeFactory.create("myType"));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlSldTemplate test = new TestXmlSldTemplate();
		test.saveReferenceXml();
    }
}