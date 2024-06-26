package org.geojsf.util.query.xpath;

import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestGeoJsfXpathView extends AbstractAhtUtilsXmlTest 
{
	final static Logger logger = LoggerFactory.getLogger(TestGeoJsfXpathView.class);
	
	private static Map xml1,xml2,xml3,xml4;
	
	@BeforeClass
	public static void initUrls()
	{
		xml1 = new Map();
		xml1.setCode("code1");
    	
		xml2 = new Map();
		xml2.setCode("code2");
    	
    	xml3 = new Map();
    	xml3.setCode("code3");
    	
    	xml4 = new Map();
    	xml4.setCode("code3");
	}
    
	private Repository createRepository()
    {
		Repository xml = new Repository();
		xml.setMaps(new Maps());
		xml.getMaps().getMap().add(xml1);
		xml.getMaps().getMap().add(xml2);
		xml.getMaps().getMap().add(xml3);
		xml.getMaps().getMap().add(xml4);
    	return xml;
    }
	
    @Test
    public void testCode1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Map actual = GeoJsfXpath.getView(createRepository(), xml1.getCode());
    	assertJaxbEquals(xml1,actual);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Map actual = GeoJsfXpath.getView(createRepository(), xml2.getCode());
    	assertJaxbEquals(xml2,actual);
    }

    @Test(expected=ExlpXpathNotFoundException.class)
    public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	GeoJsfXpath.getView(createRepository(), "-1");
    }
    
    @Test(expected=ExlpXpathNotUniqueException.class)
    public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	GeoJsfXpath.getView(createRepository(), xml3.getCode());
    }
}