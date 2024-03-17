package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStyle extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyle.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Style.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Style actual = create(true);
    	Style expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Style.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Style create(boolean withChilds)
    {
    	Style xml = new Style();
    	xml.setName("myName");
    		
    	if(withChilds)
    	{
    		xml.setWorkspace(TestXmlWorkspace.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlStyle.initFiles();	
		TestXmlStyle test = new TestXmlStyle();
		test.save();
    }
}