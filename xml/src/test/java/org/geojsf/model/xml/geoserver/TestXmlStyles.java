package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStyles extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyles.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Styles.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Styles actual = create(true);
    	Styles expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Styles.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Styles create(boolean withChilds)
    {
    	Styles xml = new Styles();
    		
    	if(withChilds)
    	{
    		xml.getStyle().add(TestXmlStyle.create(false));xml.getStyle().add(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlStyles.initFiles();	
		TestXmlStyles test = new TestXmlStyles();
		test.save();
    }
}