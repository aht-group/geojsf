package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverageStore extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverageStore.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,CoverageStore.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	CoverageStore actual = create(true);
    	CoverageStore expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CoverageStore.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static CoverageStore create(boolean withChilds)
    {
    	CoverageStore xml = new CoverageStore();
    	xml.setName("myName");
    	xml.setDescription("myDescription");
    	xml.setType("myType");
    	xml.setEnabled(true);
    	xml.setUrl("myUrl");
    	
    	if(withChilds)
    	{
    		xml.setCoverages(TestXmlCoverages.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlCoverageStore.initFiles();	
		TestXmlCoverageStore test = new TestXmlCoverageStore();
		test.save();
    }
}