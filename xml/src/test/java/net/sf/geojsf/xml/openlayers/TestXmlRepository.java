package net.sf.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

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
		fXml = new File(rootDir,"repository.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Repository actual = create();
    	Repository expected = (Repository)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Repository.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Repository create() {return create(true);}
    public static Repository create(boolean withChilds)
    {
    	Repository xml = new Repository();

    	if(withChilds)
    	{
    		xml.setViews(TestXmlViews.create(false));
    		xml.setLayers(TestXmlLayers.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlRepository.initFiles();	
		TestXmlRepository test = new TestXmlRepository();
		test.save();
    }
}