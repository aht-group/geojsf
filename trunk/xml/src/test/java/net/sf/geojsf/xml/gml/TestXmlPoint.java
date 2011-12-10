package net.sf.geojsf.xml.gml;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPoint extends AbstractXmlGmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPoint.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"point.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Point actual = create();
    	Point expected = (Point)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Point.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Point create() {return create(true);}
    public static Point create(boolean withChilds)
    {
    	Point xml = new Point();
    	xml.setSrsName("mySrsName");
    	
    	if(withChilds)
    	{
    		xml.setCoordinates(TestXmlCoordinates.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlPoint.initFiles();	
		TestXmlPoint test = new TestXmlPoint();
		test.save();
    }
}