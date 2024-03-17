package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScale extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScale.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Scale.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Scale actual = create(true);
    	Scale expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Scale.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Scale create(boolean withChilds)
    {
    	Scale xml = new Scale();
    	xml.setUnit("m");
    	xml.setValue(10000);
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlScale.initFiles();	
		TestXmlScale test = new TestXmlScale();
		test.save();
    }
}