package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScales extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScales.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Scales.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Scales actual = create(true);
    	Scales expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Scales.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Scales create(boolean withChilds)
    {
    	Scales xml = new Scales();
    	xml.setUnit("meter");
    	
    	if(withChilds)
    	{
    		xml.getScale().add(TestXmlScale.create(false));
    		xml.getScale().add(TestXmlScale.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
			
		TestXmlScales.initFiles();	
		TestXmlScales test = new TestXmlScales();
		test.save();
    }
}