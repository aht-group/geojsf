package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geoserver.ShapeDir;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlShapeDir extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlShapeDir.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,ShapeDir.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	ShapeDir actual = create(true);
    	ShapeDir expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ShapeDir.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static ShapeDir create(boolean withChilds)
    {
    	ShapeDir xml = new ShapeDir();
    	xml.setUrl("myUrl");
    	xml.setCharset("myCharSet");
    	xml.setMemoryBuffer(true);
    	xml.setCacheMemoryMaps(true);
    	
    	if(withChilds)
    	{
    		xml.setSpatial(TestXmlSpatial.create(true));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlShapeDir.initFiles();	
		TestXmlShapeDir test = new TestXmlShapeDir();
		test.save();
    }
}