package org.geojsf.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.geojsf.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMap extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMap.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Map.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Map actual = create(true);
    	Map expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Map.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Map create(boolean withChilds)
    {
    	Map xml = new Map();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setX(3.4);
    	xml.setY(5.6);
    	xml.setZoom(3);
     	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));
    		xml.setLangs(new Langs());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlMap.initFiles();	
		TestXmlMap test = new TestXmlMap();
		test.save();
    }
}