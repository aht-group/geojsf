package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLegend extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLegend.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Legend.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Legend actual = create(true);
    	Legend expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Legend.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Legend create(boolean withChilds)
    {
    	Legend xml = new Legend();
    	xml.setId(1l);
    	xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlLegend.initFiles();	
		TestXmlLegend test = new TestXmlLegend();
		test.save();
    }
}