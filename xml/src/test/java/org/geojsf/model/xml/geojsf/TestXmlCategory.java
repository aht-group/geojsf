package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

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
    	xml.setId(1);
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
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCategory.initFiles();	
		TestXmlCategory test = new TestXmlCategory();
		test.save();
    }
}