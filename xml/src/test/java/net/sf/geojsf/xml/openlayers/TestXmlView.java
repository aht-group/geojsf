package net.sf.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlView extends AbstractXmlOpenlayersTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"view.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	View actual = create();
    	View expected = (View)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), View.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static View create() {return create(true);}
    public static View create(boolean withChilds)
    {
    	View xml = new View();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setX(3.4);
    	xml.setY(5.6);
    	xml.setZoom(3);
     	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlView.initFiles();	
		TestXmlView test = new TestXmlView();
		test.save();
    }
}