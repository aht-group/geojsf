package org.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.ogc.Filter;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFilter extends AbstractXmlOgcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFilter.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"filter.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Filter actual = create();
    	Filter expected = (Filter)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Filter.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Filter create() {return create(true);}
    public static Filter create(boolean withChilds)
    {
    	Filter xml = new Filter();

    	if(withChilds)
    	{
    		xml.setDWithin(TestXmlDWithin.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlFilter.initFiles();	
		TestXmlFilter test = new TestXmlFilter();
		test.save();
    }
}