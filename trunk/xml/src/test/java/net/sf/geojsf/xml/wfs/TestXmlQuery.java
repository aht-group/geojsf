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

public class TestXmlQuery extends AbstractXmlWfsTest
{
	static Log logger = LogFactory.getLog(TestXmlQuery.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"query.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Query actual = create();
    	Query expected = (Query)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Query.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Query create() {return create(true);}
    public static Query create(boolean withChilds)
    {
    	Query xml = new Query();
    	xml.setTypeName("myTypeName");
    	
    	if(withChilds)
    	{
    		xml.setFilter(TestXmlFilter.create(false));
    		xml.getPropertyName().add(TestXmlPropertyName.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlQuery.initFiles();	
		TestXmlQuery test = new TestXmlQuery();
		test.save();
    }
}