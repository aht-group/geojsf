package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlViewPort extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPort.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, ViewPort.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	ViewPort actual = create(true);
    	ViewPort expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ViewPort.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static ViewPort create(boolean withChilds)
    {
    	ViewPort xml = new ViewPort();
    	xml.setId(123l);
    	xml.setLat(1.23);
    	xml.setLon(4.56);
    	xml.setTop(2.22);
    	xml.setLeft(3.33);
    	xml.setRight(4.44);
    	xml.setBottom(5.55);
     	
    	if(withChilds)
    	{
    		xml.setScale(TestXmlScale.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlViewPort.initFiles();	
		TestXmlViewPort test = new TestXmlViewPort();
		test.save();
    }
}