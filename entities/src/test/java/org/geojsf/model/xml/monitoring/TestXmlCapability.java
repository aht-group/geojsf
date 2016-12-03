package org.geojsf.model.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCapability extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCapability.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Capability.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Capability actual = create(true);
    	Capability expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Capability.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Capability create(boolean withChilds)
    {
    	Capability xml = new Capability();
    	xml.setId(123);
    	
    	if(withChilds)
    	{
    		xml.setType(XmlTypeFactory.create("myType"));
    		xml.setStatus(XmlStatusFactory.create("myStatus"));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCapability.initFiles();	
		TestXmlCapability test = new TestXmlCapability();
		test.save();
    }
}