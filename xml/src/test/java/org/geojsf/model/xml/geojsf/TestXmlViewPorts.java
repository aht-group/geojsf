package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViewPorts extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPorts.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, ViewPorts.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	ViewPorts actual = create(true);
    	ViewPorts expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ViewPorts.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static ViewPorts create(boolean withChilds)
    {
    	ViewPorts xml = new ViewPorts();
     	
    	if(withChilds)
    	{
    		xml.getViewPort().add(TestXmlViewPort.create(false));
    		xml.getViewPort().add(TestXmlViewPort.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlViewPorts.initFiles();	
		TestXmlViewPorts test = new TestXmlViewPorts();
		test.save();
    }
}