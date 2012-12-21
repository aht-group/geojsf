package org.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.openlayers.Layers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayers extends AbstractXmlOpenlayersTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayers.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"layers.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Layers actual = create(true);
    	Layers expected = (Layers)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layers.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Layers create(boolean withChilds)
    {
    	Layers xml = new Layers();
    	xml.setWms("myWms");
    	xml.setWcs("myWcs");
     	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));
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