package org.geojsf.xml.geojsf;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.openlayers.TestXmlLayer;
import org.geojsf.xml.openlayers.TestXmlRepository;
import org.geojsf.xml.openlayers.TestXmlService;
import org.geojsf.xml.openlayers.TestXmlView;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlGmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Query.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Query actual = create(true);
    	Query expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Query.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Query create(boolean withChilds)
    {
    	Query xml = new Query();

    	if(withChilds)
    	{
    		xml.setService(TestXmlService.create(false));
    		xml.setRepository(TestXmlRepository.create(false));
    		xml.setLayer(TestXmlLayer.create(false));
    		xml.setView(TestXmlView.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlQuery.initFiles();	
		TestXmlQuery test = new TestXmlQuery();
		test.save();
    }
}