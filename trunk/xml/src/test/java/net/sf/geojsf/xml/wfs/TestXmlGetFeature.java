package net.sf.geojsf.xml.wfs;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;
import net.sf.geojsf.xml.ogc.TestXmlFilter;
import net.sf.geojsf.xml.ogc.TestXmlPropertyName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlGetFeature extends AbstractXmlWfsTest
{
	static Log logger = LogFactory.getLog(TestXmlGetFeature.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"getFeature.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	GetFeature actual = create();
    	GetFeature expected = (GetFeature)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), GetFeature.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static GetFeature create() {return create(true);}
    public static GetFeature create(boolean withChilds)
    {
    	GetFeature xml = new GetFeature();
    	xml.setService("myService");
    	xml.setVersion("myVersion");
    	xml.setOutputFormat("myoutputFormat");
    	
    	if(withChilds)
    	{
    		xml.setQuery(TestXmlQuery.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlGetFeature.initFiles();	
		TestXmlGetFeature test = new TestXmlGetFeature();
		test.save();
    }
}