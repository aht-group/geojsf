package org.geojsf.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.geojsf.TestXmlWkt;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlData extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix, Data.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Data actual = create(true);
    	Data expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Data.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Data create(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123);
    	xml.setRange(345);
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.getValue().add(TestXmlValue.create(false));
    		xml.getValue().add(TestXmlValue.create(false));
    		xml.setWkt(TestXmlWkt.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlData.initFiles();	
		TestXmlData test = new TestXmlData();
		test.save();
    }
}