package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlName extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlName.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Name.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Name actual = create();
    	Name expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Name.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Name create()
    {
    	Name xml = new Name();
    	xml.setValue("myName");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlName.initFiles();	
		TestXmlName test = new TestXmlName();
		test.save();
    }
}