package net.sf.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;
import net.sf.geojsf.xml.gml.TestXmlPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlDWithin extends AbstractXmlOgcTest
{
	static Log logger = LogFactory.getLog(TestXmlDWithin.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"dWithin.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	DWithin actual = create();
    	DWithin expected = (DWithin)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DWithin.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static DWithin create() {return create(true);}
    public static DWithin create(boolean withChilds)
    {
    	DWithin xml = new DWithin();

    	if(withChilds)
    	{
    		xml.setPoint(TestXmlPoint.create(false));
    		xml.setDistance(TestXmlDistance.create(false));
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDWithin.initFiles();	
		TestXmlDWithin test = new TestXmlDWithin();
		test.save();
    }
}