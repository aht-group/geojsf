package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSpatial extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSpatial.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Spatial.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Spatial actual = create(true);
    	Spatial expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Spatial.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Spatial create(boolean withChilds)
    {
    	Spatial xml = new Spatial();
    	xml.setCreateIndex(true);
    	xml.setEnableIndex(true);
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlSpatial.initFiles();	
		TestXmlSpatial test = new TestXmlSpatial();
		test.save();
    }
}