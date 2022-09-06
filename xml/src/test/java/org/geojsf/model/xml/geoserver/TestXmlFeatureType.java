package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geoserver.FeatureType;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFeatureType extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFeatureType.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,FeatureType.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	FeatureType actual = create(true);
    	FeatureType expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), FeatureType.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static FeatureType create(boolean withChilds)
    {
    	FeatureType xml = new FeatureType();
    	xml.setName("myName");
    	xml.setNativeName("myNativeName");
    	
    		
    	if(withChilds)
    	{
    		xml.setDataStore(TestXmlDataStore.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlFeatureType.initFiles();	
		TestXmlFeatureType test = new TestXmlFeatureType();
		test.save();
    }
}