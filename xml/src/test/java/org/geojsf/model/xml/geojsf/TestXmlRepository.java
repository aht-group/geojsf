package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlRepository extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRepository.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Repository.class);}
    
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
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getSldTemplate().add(TestXmlSldTemplate.create(false));xml.getSldTemplate().add(TestXmlSldTemplate.create(false));
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