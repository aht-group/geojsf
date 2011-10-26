package net.sf.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlDistance extends AbstractXmlOgcTest
{
	static Log logger = LogFactory.getLog(TestXmlDistance.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"distance.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Distance actual = create();
    	Distance expected = (Distance)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Distance.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Distance create() {return create(true);}
    public static Distance create(boolean withChilds)
    {
    	Distance xml = new Distance();
    	xml.setUnits("myUnits");
    	xml.setValue("myValue");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDistance.initFiles();	
		TestXmlDistance test = new TestXmlDistance();
		test.save();
    }
}