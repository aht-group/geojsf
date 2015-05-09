package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverageStores extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverageStores.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,CoverageStores.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	CoverageStores actual = create(true);
    	CoverageStores expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CoverageStores.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static CoverageStores create(boolean withChilds)
    {
    	CoverageStores xml = new CoverageStores();
    	
    	if(withChilds)
    	{
    		xml.getCoverageStore().add(TestXmlCoverageStore.create(false));
    		xml.getCoverageStore().add(TestXmlCoverageStore.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCoverageStores.initFiles();	
		TestXmlCoverageStores test = new TestXmlCoverageStores();
		test.save();
    }
}