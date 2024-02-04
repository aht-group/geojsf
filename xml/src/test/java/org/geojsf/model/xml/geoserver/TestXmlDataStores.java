package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataStores extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataStores.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,DataStores.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	DataStores actual = create(true);
    	DataStores expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DataStores.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static DataStores create(boolean withChilds)
    {
    	DataStores xml = new DataStores();
    	
    	if(withChilds)
    	{
    		xml.getDataStore().add(TestXmlDataStore.create(false));
    		xml.getDataStore().add(TestXmlDataStore.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDataStores.initFiles();	
		TestXmlDataStores test = new TestXmlDataStores();
		test.save();
    }
}