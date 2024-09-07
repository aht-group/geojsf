package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlStyleFactory;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSldRule extends AbstractXmlGeojsfTest<SldRule>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSldRule.class);
	
	public TestXmlSldRule() {super(SldRule.class);}
	public static SldRule create(boolean withChildren){return (new TestXmlSldRule()).build(withChildren);}

    
    public SldRule build(boolean withChilds)
    {
    	SldRule xml = new SldRule();
    	xml.setId(123l);
    	xml.setSize(1);
    	xml.setLowerBound(123.45);
    	xml.setUpperBound(987.65);
    	xml.setColor("CCAABB");
    	    	
    	if(withChilds)
    	{
    		xml.setSld(TestXmlSld.create(false));
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setStyle(XmlStyleFactory.build("myStyle"));
    		xml.setGraphic(XmlGraphicFactory.build());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlSldRule test = new TestXmlSldRule();
		test.saveReferenceXml();
    }
}