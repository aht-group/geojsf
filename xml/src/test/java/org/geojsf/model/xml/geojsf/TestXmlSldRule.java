package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlStyleFactory;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSldRule extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSldRule.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, SldRule.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	SldRule actual = create(true);
    	SldRule expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), SldRule.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static SldRule create(boolean withChilds)
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
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlSldRule.initFiles();	
		TestXmlSldRule test = new TestXmlSldRule();
		test.save();
    }
}