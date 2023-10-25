package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlWkt extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWkt.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Wkt.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Wkt actual = create(true);
    	Wkt expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Wkt.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Wkt create(boolean withChilds)
    {
    	Wkt xml = new Wkt();
    	xml.setType("myType");
    	xml.setValue("myWKT");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlWkt.initFiles();	
		TestXmlWkt test = new TestXmlWkt();
		test.save();
    }
}