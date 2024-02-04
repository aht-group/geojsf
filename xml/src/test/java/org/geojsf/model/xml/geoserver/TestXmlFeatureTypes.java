package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFeatureTypes extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFeatureTypes.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,FeatureTypes.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	FeatureTypes actual = create(true);
    	FeatureTypes expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), FeatureTypes.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static FeatureTypes create(boolean withChilds)
    {
    	FeatureTypes xml = new FeatureTypes();
    	
    	if(withChilds)
    	{
    		xml.getFeatureType().add(TestXmlFeatureType.create(false));
    		xml.getFeatureType().add(TestXmlFeatureType.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlFeatureTypes.initFiles();	
		TestXmlFeatureTypes test = new TestXmlFeatureTypes();
		test.save();
    }
}