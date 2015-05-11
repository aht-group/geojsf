package org.geojsf.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
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
     	
    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    		
    		xml.setViewPort(TestXmlViewPort.create(false));
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