package net.sf.geojsf.xml.ogc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlFilter extends AbstractXmlOgcTest
{
	static Log logger = LogFactory.getLog(TestXmlFilter.class);
	
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