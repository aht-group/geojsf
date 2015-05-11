package org.geojsf.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.Sld;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSld extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSld.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Sld.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Sld actual = create(true);
    	Sld expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Sld.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Sld create(boolean withChilds)
    {
    	Sld xml = new Sld();
    	xml.setId(1);
    	    	
    	if(withChilds)
    	{
    		xml.setSldTemplate(TestXmlSldTemplate.create(false));
    		xml.getSldRule().add(TestXmlSldRule.create(false));xml.getSldRule().add(TestXmlSldRule.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlSld.initFiles();	
		TestXmlSld test = new TestXmlSld();
		test.save();
    }
}