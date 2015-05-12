package org.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.ogc.PropertyName;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPropertyName extends AbstractXmlOgcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPropertyName.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"propertyName.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	PropertyName actual = create();
    	PropertyName expected = (PropertyName)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), PropertyName.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static PropertyName create() {return create(true);}
    public static PropertyName create(boolean withChilds)
    {
    	PropertyName xml = new PropertyName();
    	xml.setValue("myValue");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlPropertyName.initFiles();	
		TestXmlPropertyName test = new TestXmlPropertyName();
		test.save();
    }
}