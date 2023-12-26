package org.geojsf.model.xml.area;

import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBasin extends AbstractXmAreaTest<Basin>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBasin.class);
	
	public TestXmlBasin(){super(Basin.class);}
	public static Basin create(boolean withChildren){return (new TestXmlBasin()).build(withChildren);}
    
    public Basin build(boolean withChilds)
    {
    	Basin xml = new Basin();
    	xml.setId(123l);
    	xml.setSize(345.67);
    	xml.setLabel("label");
    	
    	if(withChilds)
    	{
    		xml.setWkt(TestXmlWkt.create(false));
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setModel(XmlModelFactory.build("myCode"));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();	
		TestXmlBasin test = new TestXmlBasin();
		test.saveReferenceXml();
    }
}