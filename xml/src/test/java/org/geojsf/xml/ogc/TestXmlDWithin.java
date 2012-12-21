package org.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.gml.TestXmlPoint;
import org.geojsf.xml.ogc.DWithin;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDWithin extends AbstractXmlOgcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDWithin.class);
	
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