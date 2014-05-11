package org.geojsf.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlValue extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlValue.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix, Value.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Value actual = create(true);
    	Value expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Value.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Value create(boolean withChilds)
    {
    	Value xml = new Value();
    	xml.setType("myT");
    	xml.setValue("123.3");
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlValue.initFiles();	
		TestXmlValue test = new TestXmlValue();
		test.save();
    }
}