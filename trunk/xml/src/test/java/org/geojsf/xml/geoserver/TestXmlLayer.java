package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayer extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Layer.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Layer actual = create(true);
    	Layer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Layer create(boolean withChilds)
    {
    	Layer xml = new Layer();
    	xml.setName("myName");
    	xml.setTitle("myTitle");
    		
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLayer.initFiles();	
		TestXmlLayer test = new TestXmlLayer();
		test.save();
    }
}