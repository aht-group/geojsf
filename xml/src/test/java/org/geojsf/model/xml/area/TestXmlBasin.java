package org.geojsf.model.xml.area;

import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBasin extends AbstractXmAreaTest<Basin>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBasin.class);
	
	public static TestXmlBasin instance() {return new TestXmlBasin();}
	private TestXmlBasin(){super(Basin.class);}
    
    @Override public Basin build(boolean withChildren)
    {
    	Basin xml = new Basin();
    	xml.setId(123l);
    	xml.setSize(345.67);
    	xml.setLabel("label");
    	
    	if(withChildren)
    	{
    		xml.setWkt(TestXmlWkt.create(false));
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setModel(XmlModelFactory.build("myCode"));
    	}
    	
    	return xml;
    }
    
    public void save() {save(this.build(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();	
		TestXmlBasin test = new TestXmlBasin();
		test.saveReferenceXml();
    }
}