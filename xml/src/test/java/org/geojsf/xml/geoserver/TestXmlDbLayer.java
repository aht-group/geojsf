package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDbLayer extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDbLayer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,DbLayer.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	DbLayer actual = create(true);
    	DbLayer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DbLayer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static DbLayer create(boolean withChilds)
    {
    	DbLayer xml = new DbLayer();
    	xml.setName("myName");
    	xml.setTitle("myTitle");
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDbLayer.initFiles();	
		TestXmlDbLayer test = new TestXmlDbLayer();
		test.save();
    }
}