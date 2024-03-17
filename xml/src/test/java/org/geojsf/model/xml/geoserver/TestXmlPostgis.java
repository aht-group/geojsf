package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPostgis extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPostgis.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Postgis.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Postgis actual = create(true);
    	Postgis expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Postgis.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Postgis create(boolean withChilds)
    {
    	Postgis xml = new Postgis();
    	
    	if(withChilds)
    	{
    		xml.setConnection(TestXmlConnection.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlPostgis.initFiles();	
		TestXmlPostgis test = new TestXmlPostgis();
		test.save();
    }
}