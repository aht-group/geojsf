package net.sf.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestXmlLegend extends AbstractXmlOpenlayersTest
{
	static Log logger = LogFactory.getLog(TestXmlLegend.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"legend.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Legend actual = create();
    	Legend expected = (Legend)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Legend.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Legend create() {return create(true);}
    public static Legend create(boolean withChilds)
    {
    	Legend xml = new Legend();
    	xml.setId(1);
    	xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLegend.initFiles();	
		TestXmlLegend test = new TestXmlLegend();
		test.save();
    }
}