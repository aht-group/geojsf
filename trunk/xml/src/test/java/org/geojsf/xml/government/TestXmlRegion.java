package org.geojsf.xml.government;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRegion extends AbstractXmlGovernmentTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRegion.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix, Region.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Region actual = create(true);
    	Region expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Region.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Region create(boolean withChilds)
    {
    	Region xml = new Region();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
//    		xml.getDistrict().add(TestXmlDistrict.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlRegion.initFiles();	
		TestXmlRegion test = new TestXmlRegion();
		test.save();
    }
}