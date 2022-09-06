package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMaps extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMaps.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Maps.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Maps actual = create(true);
    	Maps expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Maps.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Maps create(boolean withChilds)
    {
    	Maps xml = new Maps();

    	if(withChilds)
    	{
    		xml.getMap().add(TestXmlMap.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlMaps.initFiles();	
		TestXmlMaps test = new TestXmlMaps();
		test.save();
    }
}