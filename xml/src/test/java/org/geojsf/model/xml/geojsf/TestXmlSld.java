package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSld extends AbstractXmlGeojsfTest<Sld>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSld.class);
	
	public TestXmlSld() {super(Sld.class);}
	public static Sld create(boolean withChildren){return (new TestXmlSld()).build(withChildren);}

    
    public Sld build(boolean withChilds)
    {
    	Sld xml = new Sld();
    	xml.setId(1l);
    	    	
    	if(withChilds)
    	{
    		xml.setSldTemplate(TestXmlSldTemplate.create(false));
    		xml.getSldRule().add(TestXmlSldRule.create(false));xml.getSldRule().add(TestXmlSldRule.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlSld test = new TestXmlSld();
		test.saveReferenceXml();
    }
}