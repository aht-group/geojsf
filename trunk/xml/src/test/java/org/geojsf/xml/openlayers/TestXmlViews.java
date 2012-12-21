package org.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.openlayers.Views;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViews extends AbstractXmlOpenlayersTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViews.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"views.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Views actual = create(true);
    	Views expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Views.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Views create(boolean withChilds)
    {
    	Views xml = new Views();

    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlViews.initFiles();	
		TestXmlViews test = new TestXmlViews();
		test.save();
    }
}