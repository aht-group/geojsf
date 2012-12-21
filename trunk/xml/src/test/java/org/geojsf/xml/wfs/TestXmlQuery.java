package org.geojsf.xml.wfs;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.ogc.TestXmlFilter;
import org.geojsf.xml.ogc.TestXmlPropertyName;
import org.geojsf.xml.wfs.Query;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlWfsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
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