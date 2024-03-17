package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverage extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverage.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Coverage.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Coverage actual = create(true);
    	Coverage expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Coverage.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Coverage create(boolean withChilds)
    {
    	Coverage xml = new Coverage();
    	xml.setName("myName");
    	
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlCoverage.initFiles();	
		TestXmlCoverage test = new TestXmlCoverage();
		test.save();
    }
}