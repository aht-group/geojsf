package org.geojsf.xml.xpath.openlayers;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.geojsf.test.AbstractGeoJsfXmlTest;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Map;
import org.geojsf.xml.openlayers.Maps;
import org.geojsf.xml.xpath.OpenLayersXpath;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOpenLayersXpathView extends AbstractGeoJsfXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOpenLayersXpathView.class);
	
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
    	Map actual = OpenLayersXpath.getView(createRepository(), xml1.getCode());
    	assertJaxbEquals(xml1,actual);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Map actual = OpenLayersXpath.getView(createRepository(), xml2.getCode());
    	assertJaxbEquals(xml2,actual);
    }

    @Test(expected=ExlpXpathNotFoundException.class)
    public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	OpenLayersXpath.getView(createRepository(), "-1");
    }
    
    @Test(expected=ExlpXpathNotUniqueException.class)
    public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	OpenLayersXpath.getView(createRepository(), xml3.getCode());
    }
}