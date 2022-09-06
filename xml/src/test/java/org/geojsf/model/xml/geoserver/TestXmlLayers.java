package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geoserver.Layers;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayers extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayers.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Layers.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Layers actual = create(true);
    	Layers expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layers.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Layers create(boolean withChilds)
    {
    	Layers xml = new Layers();
    		
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLayers.initFiles();	
		TestXmlLayers test = new TestXmlLayers();
		test.save();
    }
}