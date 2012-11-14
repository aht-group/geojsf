package net.sf.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestXmlLegend extends AbstractXmlOpenlayersTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLegend.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"legend.xml");
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
    	xml.setId(1);
    	xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLegend.initFiles();	
		TestXmlLegend test = new TestXmlLegend();
		test.save();
    }
}