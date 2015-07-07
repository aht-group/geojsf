package org.geojsf.model.xml.area;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.area.Areas;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAreas extends AbstractXmAreaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAreas.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Areas.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Areas actual = create(true);
    	Areas expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Areas.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Areas create(boolean withChilds)
    {
    	Areas xml = new Areas();
    	
    	if(withChilds)
    	{
    		xml.getBasin().add(TestXmlBasin.create(false));
    		xml.getBasin().add(TestXmlBasin.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlAreas.initFiles();	
		TestXmlAreas test = new TestXmlAreas();
		test.save();
    }
}