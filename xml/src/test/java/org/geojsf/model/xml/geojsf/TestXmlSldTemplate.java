package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSldTemplate extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSldTemplate.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, SldTemplate.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	SldTemplate actual = create(true);
    	SldTemplate expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), SldTemplate.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static SldTemplate create(boolean withChilds)
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
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlSldTemplate.initFiles();	
		TestXmlSldTemplate test = new TestXmlSldTemplate();
		test.save();
    }
}