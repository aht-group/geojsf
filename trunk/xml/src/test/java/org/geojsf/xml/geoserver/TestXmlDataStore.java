package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataStore extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataStore.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,DataStore.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	DataStore actual = create(true);
    	DataStore expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DataStore.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static DataStore create(boolean withChilds)
    {
    	DataStore xml = new DataStore();
    	xml.setName("myName");
    	xml.setDescription("myDescription");
    	
    	if(withChilds)
    	{
    
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDataStore.initFiles();	
		TestXmlDataStore test = new TestXmlDataStore();
		test.save();
    }
}