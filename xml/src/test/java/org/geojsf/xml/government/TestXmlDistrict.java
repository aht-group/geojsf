package org.geojsf.xml.government;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDistrict extends AbstractXmlGovernmentTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDistrict.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix, District.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	District actual = create(true);
    	District expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), District.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static District create(boolean withChilds)
    {
    	District xml = new District();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
//    		xml.setRegion(TestXmlRegion.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDistrict.initFiles();	
		TestXmlDistrict test = new TestXmlDistrict();
		test.save();
    }
}