package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayers extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayers.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Layers.class);
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
		GeoJsfBootstrap.init();
			
		TestXmlLayers.initFiles();	
		TestXmlLayers test = new TestXmlLayers();
		test.save();
    }
}