package org.geojsf.xml.openlayers;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRepository extends AbstractXmlOpenlayersTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRepository.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "repository");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Repository actual = create(true);
    	Repository expected = (Repository)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Repository.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Repository create(boolean withChilds)
    {
    	Repository xml = new Repository();

    	if(withChilds)
    	{
    		xml.setMaps(TestXmlMaps.create(false));
    		xml.setLayers(TestXmlLayers.create(false));
    		xml.getService().add(TestXmlService.create(false));xml.getService().add(TestXmlService.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlRepository.initFiles();	
		TestXmlRepository test = new TestXmlRepository();
		test.save();
    }
}