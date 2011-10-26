package net.sf.geojsf.xml.gml;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlCoordinates extends AbstractXmlGmlTest
{
	static Log logger = LogFactory.getLog(TestXmlCoordinates.class);
	
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