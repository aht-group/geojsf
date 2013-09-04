package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverages extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverages.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Coverages.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Coverages actual = create(true);
    	Coverages expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Coverages.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Coverages create(boolean withChilds)
    {
    	Coverages xml = new Coverages();
    	
    	if(withChilds)
    	{
    		xml.getCoverage().add(TestXmlCoverage.create(false));
    		xml.getCoverage().add(TestXmlCoverage.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCoverages.initFiles();	
		TestXmlCoverages test = new TestXmlCoverages();
		test.save();
    }
}