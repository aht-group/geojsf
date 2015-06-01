package org.geojsf.model.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStation extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStation.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Station.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Station actual = create(true);
    	Station expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Station.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Station create(boolean withChilds)
    {
    	Station xml = new Station();
    	xml.setId(123);
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.getCapability().add(TestXmlCapability.create(false));xml.getCapability().add(TestXmlCapability.create(false));
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setWkt(TestXmlWkt.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlStation.initFiles();	
		TestXmlStation test = new TestXmlStation();
		test.save();
    }
}