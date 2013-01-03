package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGeoserver extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGeoserver.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,GeoServer.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	GeoServer actual = create(true);
    	GeoServer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), GeoServer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static GeoServer create(boolean withChilds)
    {
    	GeoServer xml = new GeoServer();
    		
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlGeoserver.initFiles();	
		TestXmlGeoserver test = new TestXmlGeoserver();
		test.save();
    }
}