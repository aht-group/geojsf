package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterXmlTest extends AbstractXmlOgcTest<Filter>
{
	final static Logger logger = LoggerFactory.getLogger(FilterXmlTest.class);
	
	public FilterXmlTest(){super(Filter.class);}
	public static Filter create(boolean withChildren){return (new FilterXmlTest()).build(withChildren);}
    
    public Filter build(boolean withChilds)
    {
    	Filter xml = new Filter();

    	if(withChilds)
    	{
    		xml.setDWithin(DWithinXmlTest.create(false));
    		xml.setPropertyIsBetween(PropertyIsBetweenXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		FilterXmlTest test = new FilterXmlTest();
		test.saveReferenceXml();
    }
}