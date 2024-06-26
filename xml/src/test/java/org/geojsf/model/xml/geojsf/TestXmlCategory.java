package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCategory extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategory.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Category.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Category actual = create(true);
    	Category expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Category.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Category create(boolean withChilds)
    {
    	Category xml = new Category();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	    	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlCategory.initFiles();	
		TestXmlCategory test = new TestXmlCategory();
		test.save();
    }
}