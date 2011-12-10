package net.sf.geojsf.xml.gml;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoordinates extends AbstractXmlGmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoordinates.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"coordinates.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Coordinates actual = create();
    	Coordinates expected = (Coordinates)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Coordinates.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Coordinates create() {return create(true);}
    public static Coordinates create(boolean withChilds)
    {
    	Coordinates xml = new Coordinates();
    	xml.setValue("x,y");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCoordinates.initFiles();	
		TestXmlCoordinates test = new TestXmlCoordinates();
		test.save();
    }
}